import joblib
import numpy as np
import tensorflow as tf
from flask import Flask, jsonify, render_template, request
from tensorflow.keras.preprocessing.sequence import pad_sequences

# 전역 변수로 리소스 로드 - 코드 재사용성 증가. 성능 증가.
encoder = joblib.load(open('./model/labelencoder_0629.pkl', 'rb'))
tokenizer = joblib.load(open('./model/tokenizer_0629.pkl', 'rb'))
model_classification = tf.keras.models.load_model("./model/model_classification_0629.h5")

scaler = joblib.load(open('./model/scaler.pkl', 'rb'))
tokenizer2 = joblib.load(open('./model/tokenizer.pkl', 'rb'))
model_regression = tf.keras.models.load_model("./model/model_regression.h5")

app = Flask(__name__)

@app.route('/')
def main():
    # home.html을 띄워준다.
    return render_template('home.html')

@app.route('/api/item/predict/classify', methods=['POST'])
def home():
    # react에서 form으로 6개의 값을 json으로 보냄
    data = request.get_json()

    # 공백 제거
    data1 = data['a'].strip()
    data2 = data['b'].strip()
    data3 = data['c'].strip()
    data4 = data['d'].strip()
    data5 = data['e'].strip()
    data6 = data['f'].strip()

    datas = [data1, data2, data3, data4, data5, data6]
    combined_padded_sequence = tokenizer.texts_to_sequences([" ".join(datas)])

    max_len = 36
    combined_padded_sequence = pad_sequences(combined_padded_sequence, maxlen=max_len)

    # 예측
    y_pred = model_classification.predict(combined_padded_sequence)
    y_pred_result = encoder.inverse_transform(np.argmax(y_pred, axis=1))

    return jsonify({"datas": datas, "pred": y_pred_result[0]})

@app.route('/api/item/predict/regression', methods=['POST'])
def home1():
    # react에서 form으로 6개의 값을 json으로 보냄
    data = request.get_json()

    # 공백 제거
    data1 = data['a'].strip()
    data2 = data['b'].strip()
    data3 = data['c'].strip()
    data4 = data['d'].strip()
    data5 = data['e'].strip()
    data6 = data['f'].strip()
    datas = [data1, data2, data3, data4, data5, data6]
    datas = " ".join(datas)

    combined_padded_sequence = tokenizer2.texts_to_sequences([datas])

    # 시퀀스 패딩
    combined_padded_sequence = pad_sequences(combined_padded_sequence, maxlen=100)

    # 예측
    pred = model_regression.predict(combined_padded_sequence)
    # 원래 스케일로 되돌리기
    pred = scaler.inverse_transform(pred)

    return jsonify({"datas": datas, "pred": round(float(pred[0][0]))})  # 반올림

if __name__ == '__main__':
    # Flask 스타트
    app.run(host='0.0.0.0', port=5000, debug=True)
