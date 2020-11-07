from sklearn.linear_model import LinearRegression
import pandas as pd
import numpy as np
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

# Use a service account
cred = credentials.Certificate(r'services.json')
firebase_admin.initialize_app(cred)
db = firestore.client()

df = pd.read_csv('generatedData.csv')

# example to read
# users_ref = db.collection(u'users')
# docs = users_ref.stream()
#
# for doc in docs:
#     print(f'{doc.id} => {doc.to_dict()}')

reg = LinearRegression()
reg.fit(df.drop('CO2',axis='columns'), df.CO2)
prediction = []
for i in range(0, 7):
    list = [0] * 32
    list[i] = 1
    list[7] = 1
    prediction.append(reg.predict([list])[0])

json_prediction={
    u"monday": prediction[0],
    u"tuesday": prediction[1],
    u"wednesday": prediction[2],
    u"thursday": prediction[3],
    u"friday": prediction[4],
    u"saturday": prediction[5],
    u"sunday": prediction[6]
}

# example to save
doc_ref = db.collection(u'prediction').document(u'data-points')
doc_ref.set(json_prediction)

