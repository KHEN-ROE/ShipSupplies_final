# Dockerfile

# 빌드 스테이지
FROM python:3.9-slim-buster as builder

WORKDIR /app

# apt-get 관련 패키지 설치와 정리를 한 번에 처리
RUN apt-get update && apt-get install -y \
    curl \
    libfreetype6-dev \
    libpng-dev \
    && rm -rf /var/lib/apt/lists/*

# Rust와 Cargo 설치
RUN curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh -s -- -y
ENV PATH="/root/.cargo/bin:${PATH}"

# 가상환경 설정
RUN python3 -m venv venv
ENV PATH="/app/venv/bin:$PATH"

# pip를 최신 버전으로 업그레이드
RUN pip install --upgrade pip setuptools wheel

# 먼저 tensorflow를 설치
RUN pip install tensorflow==2.12.0 --default-timeout=1000

# 메모리를 많이 차지하는 패키지 먼저 설치
COPY requirements-heavy.txt .
RUN --mount=type=cache,target=/root/.cache/pip pip install -r requirements-heavy.txt

# 나머지 패키지 설치
COPY requirements.txt .
RUN --mount=type=cache,target=/root/.cache/pip pip install -r requirements.txt

# 실행 스테이지
FROM python:3.9-slim-buster

WORKDIR /app

# 가상환경 복사
COPY --from=builder /app/venv /venv

# 필요한 파일만 복사
COPY --from=builder /app/venv /root/.local
COPY . .

CMD [ "python3", "-m" , "flask", "run", "--host=0.0.0.0"]
