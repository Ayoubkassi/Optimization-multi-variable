

data = [
	[0,10,20,30,30,20],
	[10,0,25,35,20,10],
	[20,25,0,15,30,20],
	[30,35,15,0,15,25],
	[30,20,30,15,0,14],
	[20,10,20,25,14,0]
]

n = len(data)

def objectif_func(x):
	return np.sum(x)

bnds = optimize.Bounds(0, 1)

