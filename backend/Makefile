test:
	docker-compose run --rm app sh -c "python manage.py test"

lint:
	docker-compose run --rm app sh -c "flake8"

up:
	docker-compose up

down:
	docker-compose down

build:
	docker-compose build

startapp:
	docker-compose run --rm app sh -c "python manage.py startapp $(name)"