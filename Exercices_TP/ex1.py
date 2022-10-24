import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import scipy as sp

import numpy as np
from scipy import optimize




sizes = [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
values = [15,12.5,17.5,15,14.5,15,20,25,19.5,20,22,23,
		14,15,15.5,16,12.5,14,18,15,10,12.5,16,18]

bounds = optimize.Bounds(0, 1)  # 0 <= x_i <= 1
integrality = np.full_like(values, True)  # x_i are integers


#define second sizes

def getArr(k,p):
	tab = []
	position = k*4 + p 
	for i in range(len(sizes)):
	    if i != position :
		    tab.append(0)
	    else :
		    tab.append(sizes[i])
	return tab

def getSecondArray():
	tab = []
	for i in range(len(sizes)):
		if i%4 != 0: 
			tab.append(0)
		else :
			tab.append(sizes[i]*values[i])
	return tab


def getThirdArray(k):
	tab = []
	pos = k*4
	for i in range(len(sizes)):
		if i in range(pos,pos+4):
			tab.append(sizes[i])
		else :
			tab.append(0)

	return tab



sizes0_0 = getArr(0,0)
sizes2_0 = getArr(2,0)
#add some
sizes0 = np.subtract(sizes0_0,sizes2_0)


sizes0_1 = getArr(0,1)
sizes2_1 = getArr(2,1)
sizes1 = np.subtract(sizes0_1,sizes2_1)


sizes0_2 = getArr(0,2)
sizes2_2 = getArr(2,2)
sizes2 = np.subtract(sizes0_2,sizes2_2)



sizes0_3 = getArr(0,3)
sizes2_3 = getArr(2,3)
sizes3 = np.subtract(sizes0_3,sizes2_3)


sizes3_2 = getArr(3,2)
sizes3_3 = getArr(3,3)
sizes4 = np.add(sizes3_2,sizes3_3)


sizes4_2 = getArr(4,2)
sizes4_3 = getArr(4,3)
sizes5 = np.add(sizes4_2,sizes4_3)


sizes1_0 = getArr(1,0)
sizes5_0 = getArr(5,0)
sizes6 = np.add(sizes1_0,sizes5_0)

sizes1_1 = getArr(1,1)
sizes5_1 = getArr(5,1)
sizes7 = np.add(sizes1_1,sizes5_1)


sizes1_2 = getArr(1,2)
sizes5_2 = getArr(5,2)
sizes8 = np.add(sizes1_2,sizes5_2)


sizes1_3 = getArr(1,3)
sizes5_3 = getArr(5,3)
sizes9 = np.add(sizes1_3,sizes5_3)


sizes10 = getSecondArray()


c_sizes0 = getThirdArray(0) 
c_sizes1 = getThirdArray(1) 
c_sizes2 = getThirdArray(2) 
c_sizes3 = getThirdArray(3) 
c_sizes4 = getThirdArray(4) 
c_sizes5 = getThirdArray(5) 



A = np.array([sizes,sizes0,sizes1,sizes2,sizes3,sizes4,sizes5,sizes6,sizes7,sizes8,sizes9,sizes10,c_sizes0,c_sizes1,c_sizes2,c_sizes3,c_sizes4,c_sizes5])
lb= np.array([4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0])
ub= np.array([4,0,0,0,0,0,0,1,1,1,1,30,1,1,1,1,1,1])




cons = optimize.LinearConstraint(A,lb,ub)


from scipy.optimize import milp
res = milp(c=values, constraints=cons,integrality=integrality, bounds=bounds)
res.success
res.x

print(res)
