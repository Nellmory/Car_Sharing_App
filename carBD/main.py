from sqlalchemy import create_engine, Column, Integer, String, ForeignKey, DateTime
from sqlalchemy.orm import Session, as_declarative, relationship, scoped_session, sessionmaker
from flask import Flask, render_template, request, jsonify
from datetime import datetime
from markupsafe import escape
from sqlalchemy_pagination import paginate

engine = create_engine("sqlite:///carDB.db", echo=False)
app = Flask(__name__)

db_session = scoped_session(sessionmaker(autocommit=False, autoflush=False, bind=engine))


@as_declarative()
class AbstractModel:
    abstract = True


class BrandModel(AbstractModel):
    __tablename__ = 'brands'
    id = Column(Integer, autoincrement=True, primary_key=True)
    brand_name = Column(String(30))
    country = Column(String(30))


class ModelModel(AbstractModel):
    __tablename__ = 'models'
    id = Column(Integer, autoincrement=True, primary_key=True)
    brand_id = Column(Integer, ForeignKey('brands.id', ondelete='SET NULL'))
    model_name = Column(String(30))


class CarModel(AbstractModel):
    __tablename__ = 'cars'
    id = Column(Integer, autoincrement=True, primary_key=True)
    vin_num = Column(String, nullable=False)
    license_plate = Column(String, nullable=False)
    model_id = Column(Integer, ForeignKey('models.id', ondelete='SET NULL'))
    colour = Column(String(20))


class ClientModel(AbstractModel):
    __tablename__ = 'clients'
    id = Column(Integer, autoincrement=True, primary_key=True)
    name = Column(String, nullable=False)
    surname = Column(String, nullable=False)
    telephone = Column(String, nullable=False)


class ViolationModel(AbstractModel):
    __tablename__ = 'violations'
    id = Column(Integer, autoincrement=True, primary_key=True)
    description = Column(String, nullable=False)
    date = Column(DateTime)
    fine = Column(Integer)
    client_id = Column(Integer, ForeignKey('clients.id', ondelete='SET NULL'))

    rents = relationship('RentViolationModel', back_populates='violation', cascade='all, delete-orphan')


class TariffModel(AbstractModel):
    __tablename__ = 'tariffs'
    id = Column(Integer, autoincrement=True, primary_key=True)
    description = Column(String, nullable=False)
    cost = Column(Integer, nullable=False)


class RentModel(AbstractModel):
    __tablename__ = 'rents'
    id = Column(Integer, autoincrement=True, primary_key=True)
    start_date = Column(DateTime)
    finish_date = Column(DateTime)
    tariff = Column(Integer, ForeignKey('tariffs.id', ondelete='SET NULL'))
    car_id = Column(Integer, ForeignKey('cars.id', ondelete='SET NULL'))
    client_id = Column(Integer, ForeignKey('clients.id', ondelete='SET NULL'))

    violations = relationship('RentViolationModel', back_populates='rent', cascade='all, delete-orphan')


class StatusModel(AbstractModel):
    __tablename__ = 'statuses'
    id = Column(Integer, autoincrement=True, primary_key=True)
    status = Column(String, nullable=False)


class MethodModel(AbstractModel):
    __tablename__ = 'paying_methods'
    id = Column(Integer, autoincrement=True, primary_key=True)
    method = Column(String, nullable=False)


class PaymentModel(AbstractModel):
    __tablename__ = 'payments'
    id = Column(Integer, autoincrement=True, primary_key=True)
    date = Column(DateTime)
    rent_id = Column(Integer, ForeignKey('rents.id', ondelete='SET NULL'))
    method_id = Column(Integer, ForeignKey('paying_methods.id', ondelete='SET NULL'))
    status_id = Column(Integer, ForeignKey('statuses.id', ondelete='SET NULL'))


class RentViolationModel(AbstractModel):
    __tablename__ = 'rent_violation'
    rent_id = Column(Integer, ForeignKey('rents.id', ondelete='CASCADE'), primary_key=True)
    violation_id = Column(Integer, ForeignKey('violations.id', ondelete='CASCADE'), primary_key=True)

    rent = relationship('RentModel', back_populates='violations')
    violation = relationship('ViolationModel', back_populates='rents')


AbstractModel.metadata.drop_all(engine)
AbstractModel.metadata.create_all(engine)


@app.route('/')
def index():
    return render_template('index.html')


@app.route('/brands')
def brands():
    session = db_session()
    brands = session.query(BrandModel).all()
    session.close()
    brands_list = [{'id': brand.id, 'brand_name': brand.brand_name, 'country': brand.country} for brand in brands]
    return jsonify(brands_list)


@app.route('/models')
def models():
    session = db_session()
    models = session.query(ModelModel).all()
    session.close()
    models_list = [{'id': model.id, 'brand_id': model.brand_id, 'model_name': model.model_name} for model in models]
    return jsonify(models_list)


@app.route('/statuses')
def statuses():
    session = db_session()
    statuses = session.query(StatusModel).all()
    session.close()
    statuses_list = [{'id': status.id, 'status': status.status} for status in statuses]
    return jsonify(statuses_list)


@app.route('/paying_methods')
def paying_methods():
    session = db_session()
    methods = session.query(MethodModel).all()
    session.close()
    methods_list = [{'id': method.id, 'method': method.method} for method in methods]
    return jsonify(methods_list)


@app.route('/clients', methods=['GET'])
def clients():
    page = request.args.get('page', 1, type=int)
    per_page = request.args.get('per_page', 10, type=int)

    session = db_session()
    clients = session.query(ClientModel)
    paginated_clients = paginate(clients, page, per_page)
    session.close()

    clients_list = [{'id': client.id, 'name': client.name, 'surname': client.surname, 'telephone': client.telephone} for
                    client in paginated_clients.items]

    result = {
        'clients': clients_list,
        'total_pages': paginated_clients.pages,
        'current_page': page,
        'has_next': paginated_clients.has_next,
        'has_prev': paginated_clients.has_previous
    }
    return jsonify(result)


@app.route('/add_client', methods=['POST'])
def add_client():
    data = request.get_json()
    if not data:
        return jsonify({'error': 'Request body must be JSON'}), 400
    try:
        name = escape(data['name'])
        surname = escape(data['surname'])
        telephone = escape(data['telephone'])
    except KeyError:
        return jsonify({'error': 'Missing required fields'}), 400

    session = db_session()
    try:
        new_client = ClientModel(name=name, surname=surname, telephone=telephone)
        session.add(new_client)
        session.commit()
    except Exception as e:
        session.rollback()
        return jsonify({'error': f"Failed to add client: {str(e)}"}), 500
    finally:
        session.close()

    return jsonify({'message': 'Client added successfully'}), 201


@app.route('/edit_client/<int:client_id>', methods=['PUT'])
def edit_client(client_id):
    data = request.get_json()
    if not data:
        return jsonify({'error': 'Request body must be JSON'}), 400
    try:
        name = escape(data['name'])
        surname = escape(data['surname'])
        telephone = escape(data['telephone'])
    except KeyError:
        return jsonify({'error': 'Missing required fields'}), 400

    session = db_session()
    try:
        client = session.query(ClientModel).get(client_id)
        client.name = name
        client.surname = surname
        client.telephone = telephone
        session.commit()
    except Exception as e:
        session.rollback()
        return jsonify({'error': f"Failed to add client: {str(e)}"}), 500
    finally:
        session.close()

    return jsonify({'message': 'Client edited successfully'}), 201


@app.route('/delete_client/<int:client_id>', methods=['DELETE'])
def delete_client(client_id):
    session = db_session()
    try:
        client = session.query(ClientModel).get(client_id)
        session.delete(client)
        session.commit()
    except Exception as e:
        session.rollback()
        return jsonify({'error': f"Failed to delete client: {str(e)}"}), 500
    finally:
        session.close()

    return jsonify({'message': 'Client deleted successfully'}), 201


@app.route('/tariffs')
def tariffs():
    session = db_session()
    tariffs = session.query(TariffModel).all()
    session.close()
    tariffs_list = [{'id': tariff.id, 'description': tariff.description, 'cost': tariff.cost} for tariff in tariffs]
    return jsonify(tariffs_list)


@app.route('/cars')
def cars():
    session = db_session()
    cars = session.query(CarModel).all()
    session.close()
    cars_list = [{'id': car.id, 'vin_num': car.vin_num, 'license_plate': car.license_plate, 'model_id': car.model_id,
                  'colour': car.colour} for car in cars]
    return jsonify(cars_list)


@app.route('/payments')
def payments():
    session = db_session()
    payments = session.query(PaymentModel).all()
    session.close()
    payments_list = [{'id': payment.id, 'date': payment.date, 'rent_id': payment.rent_id,
                      'method_id': payment.method_id, 'status_id': payment.status_id} for payment in payments]
    return jsonify(payments_list)


@app.route('/rent_violation')
def rent_violation():
    session = db_session()
    rent_violations = session.query(RentViolationModel).all()
    session.close()
    rent_violations_list = [{'rent_id': rent_viol.rent_id,
                             'violation_id': rent_viol.violation_id} for rent_viol in rent_violations]
    return jsonify(rent_violations_list)


@app.route('/violations')
def violations():
    session = db_session()
    violations = session.query(ViolationModel).all()
    session.close()
    violations_list = [{'id': violation.id, 'description': violation.description, 'date': violation.date,
                        'fine': violation.fine, 'client_id': violation.client_id} for violation in violations]
    return jsonify(violations_list)


@app.route('/rents', methods=['GET'])
def rents():
    page = request.args.get('page', 1, type=int)
    per_page = request.args.get('per_page', 10, type=int)

    session = db_session()
    rents = session.query(RentModel)
    paginated_rents = paginate(rents, page, per_page)
    session.close()

    rents_list = [{'id': rent.id, 'start_date': rent.start_date, 'finish_date': rent.finish_date, 'tariff': rent.tariff,
                   'car_id': rent.car_id, 'client_id': rent.client_id} for rent in paginated_rents.items]

    result = {
        'rents': rents_list,
        'total_pages': paginated_rents.pages,
        'current_page': page,
        'has_next': paginated_rents.has_next,
        'has_prev': paginated_rents.has_previous
    }
    return jsonify(result)


@app.route('/add_rent', methods=['POST'])
def add_rent():
    data = request.get_json()
    if not data:
        return jsonify({'error': 'Request body must be JSON'}), 400
    try:
        start_date_str = escape(data['start_date'])
        finish_date_str = escape(data['finish_date'])
        tariff = escape(data['tariff'])
        car_id = escape(data['car_id'])
        client_id = escape(data['client_id'])

        start_date = datetime.strptime(start_date_str, '%Y-%m-%dT%H:%M')
        finish_date = datetime.strptime(finish_date_str, '%Y-%m-%dT%H:%M')
    except KeyError:
        return jsonify({'error': 'Missing required fields'}), 400

    session = db_session()
    try:
        new_rent = RentModel(
            start_date=start_date,
            finish_date=finish_date,
            tariff=tariff,
            car_id=car_id,
            client_id=client_id
        )
        session.add(new_rent)
        session.commit()
    except Exception as e:
        session.rollback()
        return jsonify({'error': f"Failed to add client: {str(e)}"}), 500
    finally:
        session.close()

    return jsonify({'message': 'Rent added successfully'}), 201


@app.route('/edit_rent/<int:rent_id>', methods=['PUT'])
def edit_rent(rent_id):
    data = request.get_json()
    if not data:
        return jsonify({'error': 'Request body must be JSON'}), 400
    try:
        start_date_str = escape(data['start_date'])
        finish_date_str = escape(data['finish_date'])
        tariff = escape(data['tariff'])
        car_id = escape(data['car_id'])
        client_id = escape(data['client_id'])

        start_date = datetime.strptime(start_date_str, '%Y-%m-%dT%H:%M')
        finish_date = datetime.strptime(finish_date_str, '%Y-%m-%dT%H:%M')
    except KeyError:
        return jsonify({'error': 'Missing required fields'}), 400

    session = db_session()
    try:
        rent = session.query(RentModel).get(rent_id)
        rent.start_date = start_date
        rent.finish_date = finish_date
        rent.tariff = tariff
        rent.car_id = car_id
        rent.client_id = client_id
        session.commit()
    except Exception as e:
        session.rollback()
        return jsonify({'error': f"Failed to add client: {str(e)}"}), 500
    finally:
        session.close()

    return jsonify({'message': 'Rent edited successfully'}), 201


@app.route('/delete_rent/<int:rent_id>', methods=['DELETE'])
def delete_rent(rent_id):
    session = db_session()
    try:
        rent = session.query(RentModel).get(rent_id)
        session.delete(rent)
        session.commit()
    except Exception as e:
        session.rollback()
        return jsonify({'error': f"Failed to delete client: {str(e)}"}), 500
    finally:
        session.close()

    return jsonify({'message': 'Rent deleted successfully'}), 201


from generation import generation

if __name__ == "__main__":
    with Session(bind=engine) as session:
        generation(session)
        app.run(debug=True, host='0.0.0.0', port=5000)
