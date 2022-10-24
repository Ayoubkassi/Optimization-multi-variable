import matplotlib.pyplot as plt
import pandas as pd
import numpy as np
import scipy as sp
from scipy import optimize
from scipy.optimize import milp
from scipy.optimize import minimize

# Construction d'usines

# je dois trouve : 
#       annee1 -> ville1 -> a
#       annee2 -> ville2 -> b
#       annee3 -> ville3 -> c
#       annee4 -> ville4 -> d

# je dois minimiser les cout d'instruction :

# la somme de i sur villes et somme de j sur annee :

# min de la somme de Cij * Xij

#X1j = X3j => X1j <= X3j et X3j <= X1j

#X21 <= X51

data = [
		[15,12.5,17.5,15],
		[14.5,15,20,25],
		[19.5,20,22,23],
		[14,15,15.5,16],
		[12.5,14,18,15],
		[10,12.5,16,18]
		]

N = len(data)
A = len(data[0])

def objectif_func(x):
	# x is matrix 2d so we can traverse row and column
	# row i is city and j is year
	sum = 0
	for i in range (N):
		for j in range(A):
			sum+=data[i][j]*x[i][j]
	return sum


#constraints
#initialisation
def constraints_1(x):
	sum = 0
	for i in range(N):
		for j in range(A):
			sum+=x[i][j]
	return sum - 4

constraint1 = { 'type' : 'eq' , 'fun' : constraints_1 }	
cons = [constraint1]


bnds = optimize.Bounds(0, 1)

x0 = [
	[1,1,1,1],
	[1,1,1,1],
	[1,1,1,1],
	[1,1,1,1],
	[1,1,1,1],
	[1,1,1,1]
]




result = minimize(objectif_func,x0,method='SLSQP',bounds=bnds, constraints=cons)

print(result)

