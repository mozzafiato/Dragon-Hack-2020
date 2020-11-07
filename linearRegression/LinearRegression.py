from sklearn import linear_model
import pandas as pd

df = pd.read_csv('generatedData.csv')

reg = linear_model.LinearRegression()
reg.fit(df.drop('CO2',axis='columns'), df.CO2)
prediction = []
for i in range(0, 7):
    list = [0] * 32
    list[i] = 1
    list[7] = 1
    prediction.append(reg.predict([list])[0])


import numpy as np
np.savetxt("predictedData.csv", prediction, delimiter=",", fmt='%s')
