test:
	docker-compose run --rm app sh -c "python manage.py test"

lint:
	docker-compose run --rm app sh -c "flake8"

startproject:
	docker-compose run --rm app sh -c "django-admin startproject app ."

up:
	docker-compose up -d