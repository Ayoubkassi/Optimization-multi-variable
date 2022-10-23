import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import scipy as sp

import numpy as np
from scipy import optimize

data = [
	[0,10,20,30,30,20],
	[10,0,25,35,20,10],
	[20,25,0,15,30,20],
	[30,35,15,0,15,25],
	[30,20,30,15,0,14],
	[20,10,20,25,14,0]
]

alpha = 15

convertedData = []

for i in range(len(data)):
	current = []
	for j in range(len(data)):
		if data[i][j] <= alpha :
			current.append(1) 
		else : 
			current.append(0)
	convertedData.append(current)

#print for test
for row in convertedData:
	print(row)

sizes = np.array(convertedData)
values = np.array([1,1,1,1,1,1])



bounds = optimize.Bounds(0, 1)  # 0 <= x_i <= 1
integrality = np.full_like(values, True)  # x_i are integers

def calcSumForI(i):
	curr = []
	for j in range(len(convertedData)) :
		res = 0
		res+=convertedData[i][j]*values[j]
		curr.append(res)
	return curr

def giveArray(k):
	tab = np.zeros(6)
	tab[0] = k
	return tab

def getArr(k):
	tab = []
	for i in range(len(values)):
	    if i != k :
		    tab.append(0)
	    else :
		    tab.append(values[i])
	return tab

cond1 = calcSumForI(0)
cond2 = calcSumForI(1)
cond3 = calcSumForI(2)
cond4 = calcSumForI(3)
cond5 = calcSumForI(4)
cond6 = calcSumForI(5)
cond7 = getArr(0)
cond_pre_8 = np.add(getArr(0),getArr(1))
cond8   = np.add(cond_pre_8,getArr(2))
arr4 = getArr(4)
arr5 = getArr(5)
cond9 = np.add(arr4,arr5)



A  = np.array([cond1,cond2,cond3,cond4,cond5,cond6,cond7,cond8,cond9])
lb = np.array([1,1,1,1,1,1,0,1,1])
ub = np.array([np.inf,np.inf,np.inf,np.inf,np.inf,np.inf,values[3],3,1])



cons = optimize.LinearConstraint(A,lb,ub)

from scipy.optimize import milp
res = milp(c=values, constraints=cons,integrality=integrality, bounds=bounds)
res.success
res.x

print(res)



# n = len(data)

# def objectif_func(x):
# 	return np.sum(x)

# bnds = optimize.Bounds(0, 1)


