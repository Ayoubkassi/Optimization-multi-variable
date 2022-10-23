import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import scipy as sp

import numpy as np
from scipy import optimize




sizes = np.array([1, 1, 1, 1, 1, 1])
values = np.array([1.4,2.7,2.4,2.9,3.5,2])

bounds = optimize.Bounds(0, 1)  # 0 <= x_i <= 1
integrality = np.full_like(values, True)  # x_i are integers


#define second sizes

def getArr(k):
	tab = []
	for i in range(len(sizes)):
	    if i != k :
		    tab.append(0)
	    else :
		    tab.append(sizes[i])
	return tab

# def get2Arr(k,p):
# 	tab = []
# 	for i in range(len(sizes)):
# 	    if i != k or i != p:
# 		    tab.append(0)
# 	    else :
# 		    tab.append(sizes[i])
# 	return tab

sizes0 = getArr(0)
sizes3 = getArr(3)
sizes5 = getArr(5)
sizes0_5 = np.add(sizes0,sizes5)

A = np.array([sizes,sizes0,sizes3,sizes3,sizes0_5])
lb= np.array([4,0,sizes[4],1-sizes[1]-sizes[4],1])
ub= np.array([4,1-sizes[3],1,1,1])

cons = optimize.LinearConstraint(A,lb,ub)


from scipy.optimize import milp
res = milp(c=-values, constraints=cons,integrality=integrality, bounds=bounds)
res.success
res.x

print(res)