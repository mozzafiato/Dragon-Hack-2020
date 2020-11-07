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

np.savetxt("predictedData.csv", prediction, delimiter=",", fmt='%s')

# example to save
# doc_ref = db.collection(u'prediction').document(u'data-points')
# doc_ref.setdoc_ref.set({
#     u'first': u'Alan',
#     u'middle': u'Mathison',
#     u'last': u'Turing',
#     u'born': 1912
# })
