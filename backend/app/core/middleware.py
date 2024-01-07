import logging


class LoggingMiddleware:
    def __init__(self, get_response):
        self.get_response = get_response
        self.logger = logging.getLogger("django.server")

    def __call__(self, request):
        self.logger.info(f"Request: {request.method} {request.get_full_path()}")
        self.logger.info(f"Headers: {request.headers}")
        self.logger.info(f"Body: {request.body}")
        self.logger.info(f"-" * 50)

        response = self.get_response(request)
        self.logger.info(f"Response: {response.status_code}")
        self.logger.info(f"Body: {response.content}")
        self.logger.info(f"=" * 50)

        return response
