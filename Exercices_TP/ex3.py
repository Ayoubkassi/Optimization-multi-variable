import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import scipy as sp
from scipy import optimize
from scipy.optimize import milp
from scipy.optimize import minimize


data = [1.4,2.7,2.4,2.9,3.5,2]
n = len(data)

def objectif_func(x):
	sum =0
	for i in range(n):
		sum+=data[i]*x[i]
	return sum 

def constraint_1(x):
	return np.sum(x) - 4 

def constraint_2(x):
	return  -x[0] - x[3] + 1

def constraint_3(x):
	return x[3] - x[4]

def constraint_4(x):
	return x[1] + x[3] + x[4] - 1

def constraint_5(x):
	return x[0] + x[5] - 1





x0 = np.ones(n)
bnds = optimize.Bounds(0, 1)
integrality = np.full_like(x0, True) 

constraint1 = { 'type' :'eq'     , 'fun' : constraint_1 }
constraint2 = { 'type' : 'ineq'  , 'fun' : constraint_2 }
constraint3 = { 'type' : 'ineq'  , 'fun' : constraint_3 }
constraint4 = { 'type' : 'ineq'  , 'fun' : constraint_4 }
constraint5 = { 'type' : 'eq'    , 'fun' : constraint_5 }
cons = [constraint1,constraint2,constraint3,constraint4,constraint5]




result = minimize(objectif_func,x0,method='SLSQP',bounds=bnds, constraints=cons,options={'ftol':1e-00})

print(result)