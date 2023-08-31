FROM python:3

RUN mkdir -p /opt/src/applications
WORKDIR /opt/src/applications

COPY application.py ./application.py
COPY role_check.py ./role_check.py
COPY configuration.py ./configuration.py
COPY models.py ./models.py
COPY requirements.txt ./requirements.txt
COPY contracts ./contracts

RUN pip install -r ./requirements.txt
ENV PYTHONPATH="/opt/src/applications"

ENTRYPOINT ["python", "./application.py"]
