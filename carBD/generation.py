import random
import string
from __main__ import (BrandModel, ModelModel, CarModel, StatusModel, MethodModel, ClientModel, TariffModel,
                      ViolationModel, RentModel, PaymentModel, RentViolationModel)
from datetime import datetime, timedelta

clients_number = 50
rents_number = 50

# БРЕНДЫ

brand_names = [
    "Audi", "BMW", "Chevrolet", "Dodge", "Ford",
    "Honda", "Hyundai", "Jeep", "Kia", "Lexus",
    "Mazda", "Mercedes-Benz", "Nissan", "Porsche",
    "Subaru", "Tesla", "Toyota", "Volkswagen", "Volvo",
    "Jaguar"
]

countries = [
    "United States", "Germany", "Japan", "South Korea",
    "Italy", "France", "United Kingdom", "China",
    "India", "Sweden", "Spain", "Canada",
    "Brazil", "Mexico", "Russia", "Czech Republic",
    "Turkey", "Thailand", "Malaysia", "Australia"
]


def generate_brands(session):
    for _ in range(10):
        brand = BrandModel(
            brand_name=random.choice(brand_names),
            country=random.choice(countries)
        )
        session.add(brand)
    session.commit()


# МОДЕЛИ

model_names = [
    "Mustang", "Corvette", "Camry", "Civic", "3 Series", "C-Class",
    "A4", "Altima", "Sonata", "Optima", "Impreza", "Mazda3",
    "Golf", "911", "Model S", "ES", "Wrangler", "Charger", "S60", "F-Type"
]


def generate_models(session):
    brand_ids = [brand.id for brand in session.query(BrandModel).all()]

    for _ in range(10):
        model_name = random.choice(model_names)
        brand_id = random.choice(brand_ids) if brand_ids else None

        new_model = ModelModel(model_name=model_name, brand_id=brand_id)
        session.add(new_model)

    session.commit()


# МАШИНЫ

def generate_vin():
    characters = string.ascii_uppercase + string.digits
    return ''.join(random.choices(characters, k=17))


def generate_license_plate():
    letters = string.ascii_uppercase
    digits = string.digits
    return f"{random.choice(letters)}{random.randint(100, 999)}{random.choice(letters)}{random.choice(letters)}"


def generate_car_models(session):
    colors = ['Red', 'Blue', 'Green', 'Black', 'White']

    models = session.query(ModelModel).all()

    for _ in range(10):
        if models:
            model = random.choice(models)
            vin_num = generate_vin()
            license_plate = generate_license_plate()
            color = random.choice(colors)
            car_model = CarModel(vin_num=vin_num, license_plate=license_plate, model_id=model.id, colour=color)
            session.add(car_model)

    session.commit()


# СТАТУСЫ

brand_names_st = ["Pending", "Successful", "Declined", "Cancelled", "Refunded"]


def generate_statuses(session):
    for st in brand_names_st:
        status = StatusModel(
            status=st
        )
        session.add(status)
    session.commit()


# МЕТОДЫ

methods = ["Credit Card", "Bank Transfer", "Cash", "E-wallet", "Cryptocurrency", "Gift Card"]


def generate_methods(session):
    for mth in methods:
        method = MethodModel(
            method=mth
        )
        session.add(method)
    session.commit()


# КЛИЕНТЫ

names = [
    "Ivan", "Petr", "Svetlana", "Olga", "Dmitry", "Sergey",
    "Anna", "Maria", "Alexey", "Nikita", "Yulia", "Vladimir",
    "Ekaterina", "Andrey", "Tatiana", "Maxim", "Artem",
    "Elena", "Oleg", "Irina", "Denis", "Natalia", "Victor",
    "Sofia", "Mikhail", "Daria", "Alexandra", "Stepan",
    "Ksenia", "Yevgeny", "Anastasia", "Roman"
]

surnames = [
    "Ivanov", "Petrov", "Sidorov", "Smirnov", "Kuznetsov",
    "Popov", "Vasiliev", "Pavlov", "Lebedev", "Morozov",
    "Novikov", "Solovyov", "Mikhailov", "Fedorov",
    "Gusakov", "Kolesnikov", "Borisov", "Nikiforov",
    "Dmitriev", "Vinogradov", "Alekseev", "Semyonov",
    "Karpov", "Tarasov", "Zaitsev", "Frolov",
    "Ermolaev", "Zhuravlev", "Belyakov", "Gromov",
    "Savelyev"
]


def generate_clients(session):
    for _ in range(clients_number):
        name = random.choice(names)
        surname = random.choice(surnames)
        telephone = '8' + ''.join(random.choices('0123456789', k=10))

        new_client = ClientModel(name=name, surname=surname, telephone=telephone)
        session.add(new_client)

    session.commit()


# ТАРИФЫ

descriptions = ["Basic Hourly Rate", "Daily Rate", "Weekly Rate", "Monthly Rate", "Premium Hourly Rate"]

costs = [300, 1000, 6500, 25000, 500]


def generate_tariffs(session):
    for d, c in zip(descriptions, costs):
        tariff = TariffModel(
            description=d,
            cost=c,
        )
        session.add(tariff)
    session.commit()


# НАРУШЕНИЯ

violations = ["Speeding", "Parking violation", "Damage to vehicle",
              "Driving without license", "Running red light", "Improper lane change",
              "Failure to yield", "Driving under influence", "Reckless driving", "Unauthorized passenger",
              "Smoking in vehicle", "Leaving vehicle unattended", "Driving in restricted area",
              "Failure to report accident", "Using phone while driving", "Exceeding rental time",
              "Unauthorized fuel type", "Driving with expired license", "Failure to return vehicle",
              "Driving without seatbelt"]

fines = [1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000]


def generate_violations(session):
    clients = session.query(ClientModel).all()

    for v in violations:
        if clients:
            client_id = random.choice(clients)
            date = datetime.now() - timedelta(days=random.randint(1, 365))
            fine = random.choice(fines)
            violation = ViolationModel(description=v, date=date, fine=fine, client_id=client_id.id)
            session.add(violation)

    session.commit()


# АРЕНДЫ

def random_date(start, end):
    return start + timedelta(days=random.randint(0, (end - start).days))


def generate_rents(session):
    tariffs = session.query(TariffModel).all()
    cars = session.query(CarModel).all()
    clients = session.query(ClientModel).all()

    if not tariffs or not cars or not clients:
        print("Нет доступных тарифов, автомобилей или клиентов для генерации.")
        return

    for _ in range(rents_number):
        start_date = random_date(datetime.now() - timedelta(days=5 * 365), datetime.now())
        finish_date = random_date(start_date, start_date + timedelta(
            days=random.randint(1, 600)))

        tariff_id = random.choice(tariffs).id
        car_id = random.choice(cars).id
        client_id = random.choice(clients).id

        new_rent = RentModel(
            start_date=start_date,
            finish_date=finish_date,
            tariff=tariff_id,
            car_id=car_id,
            client_id=client_id
        )

        session.add(new_rent)

    session.commit()


# ОПЛАТЫ

def generate_payments(session):
    methods = session.query(MethodModel).all()
    statuses = session.query(StatusModel).all()
    rents = session.query(RentModel).all()

    if not methods or not statuses or not rents:
        print("Нет доступных методов оплаты, статусов оплаты или аренд для генерации.")
        return

    for _ in range(10):
        date = random_date(datetime.now() - timedelta(days=3 * 365), datetime.now())

        rent_id = random.choice(rents).id
        method_id = random.choice(methods).id
        status_id = random.choice(statuses).id

        new_payment = PaymentModel(
            date=date,
            rent_id=rent_id,
            method_id=method_id,
            status_id=status_id
        )

        session.add(new_payment)

    session.commit()


# АРЕНДЫ-ОПЛАТЫ

def generate_rent_violations(session):
    results = session.query(RentModel, ViolationModel).join(ViolationModel,
                                                            RentModel.client_id == ViolationModel.client_id).all()

    if not results:
        print("Нет доступных записей о аренде или нарушениях для генерации.")
        return

    for rent, violation in results:
        new_rent_violation = RentViolationModel(
            rent_id=rent.id,
            violation_id=violation.id
        )

        session.add(new_rent_violation)

    session.commit()


# ГЕНЕРАЦИЯ

def generation(session):
    # ветка машин
    generate_brands(session)
    generate_models(session)
    generate_car_models(session)
    # ветка оплаты
    generate_statuses(session)
    generate_methods(session)
    # ветка клиент-нарушения-аренда
    generate_clients(session)
    generate_tariffs(session)
    generate_violations(session)
    generate_rents(session)
    generate_payments(session)
    # многое-ко_многому
    generate_rent_violations(session)
