import numpy as np
import scipy.signal as sig
import matplotlib.pyplot as pp
from math import cos, sin

l_L = 0.04
l_P = 0.04
l_T = 0.02
l_k = 0.04
r = 0.05
m = 1.25
k_v = 0.1
J_t = 0.55
k_omega = 1.350

R = 2.0
L = 0.05
K = 0.1
R_z = 0.2
U_0 = 10.0
J = 0.025
k_r = 0.002
p_G  = 25.0


r_G = r /p_G
a_L = k_r + k_v*l_P*r_G*r_G/(l_P +l_L)
a_P = k_r + k_v*l_L*r_G*r_G/(l_P +l_L)
b_L = J + m*l_P*r_G*r_G / (l_P +l_L)
b_P = J + m*l_L*r_G*r_G / (l_P +l_L)

c_coom = k_omega*r_G*r_G / (l_P +l_L)
c_L = k_r*l_L+ c_coom
c_P = k_r*l_P+ c_coom

J_B = J_t+m*l_T*l_T
d_coom = J_B*r_G*r_G / (l_P +l_L)
d_L = J*l_L + d_coom
d_P = J*l_P + d_coom

a00 = -1.0*(R+R_z)/L
a01 =-1.0*(R_z)/L
a02 =-1.0*(K)/L
a03 = 0

a10 =-1.0*(R_z)/L
a11 = -1.0*(R+R_z)/L
a12 = 0
a13 =-1.0*(K)/L

a2_den = b_L*d_P + b_P*d_L
a20 = K* (d_P +b_P*l_L)/a2_den
a21 = K* (d_P -b_P*l_P)/a2_den
a22  = -1.0*(d_P*a_L + b_P*c_L)/a2_den
a23  = -1.0*(d_P*a_P - b_P*c_P)/a2_den

a30 = K*(d_L-b_L*l_L) / a2_den
a31 = K*(d_L + b_L*l_P) / a2_den
a32  = -1.0*(d_L*a_L - b_L*c_L)/a2_den
a33  = -1.0*(d_L*a_P + b_L*c_P)/a2_den

b00 = U_0 / L
b11 = U_0 / L

l_sum = (l_L + l_P)
c02 = l_P*r_G/l_sum
c03 = l_L*r_G/l_sum
c12 = -1.0*r_G/l_sum
c13 = r_G/l_sum





A = np.array([
    [a00, a01, a02, a03],
    [a10, a11, a12, a13],
    [a20, a21, a22, a23],
    [a30, a31, a32, a33],
    ])
B = np.array([
    [b00, 0.0],
    [0.0, b11],
    [0.0, 0.0],
    [0.0, 0.0],
    ])
C = np.array([
    [0.0, 0.0, c02, c03],
    [0.0, 0.0, c12, c13],
    ])
D = np.zeros((2,2))

cons_sys = sig.StateSpace(A,B,C,D);
# tout, y, x = sig.lsim(cons_sys, np.ones((100,2))*1, np.linspace(0,100,100))

dt = 0.05
dis_sys = sig.cont2discrete((A,B,C,D), dt)


# tout, y, x = sig.dlsim(discrete_rendsze, np.ones((100,2)), np.linspace(0,100,100))
# pp.plot(tout, y)
# pp.show()
sim_time = 100
(dA, dB, dC, dD,_) = dis_sys

print dC


X = np.zeros(4)
time = []
u = np.array([10.0, 10.0]).T
rezs =np.zeros((sim_time, 2))
Xs =np.zeros((sim_time, 4))
x =np.zeros(sim_time)
y = np.zeros(sim_time)
alpha_k = 0.0
x_k = 0.0
y_k = 0.0



for t in range(0, sim_time):
    time.append(t)
    X_n = dA.dot(X) + dB.dot(u)
    rez = dC.dot(X) + dD.dot(u)
    X = X_n
    rezs[t,0] = rez[0]
    rezs[t,1] = rez[1]
    alpha_n = alpha_k + rez[1] * dt
    v_x = rez[0]*cos(alpha_k)
    v_y = rez[0]*sin(alpha_k)
    x_n = x_k + dt*v_x
    y_n = y_k + dt*v_y
    Xs[t,0] = X[0]
    Xs[t,1] = X[1]
    Xs[t,2] = X[2]
    Xs[t,3] = X[3]

    x[t] = x_k
    y[t] = y_k
    alpha_k = alpha_n
    x_k = x_n
    y_k = y_n



pp.plot(x, y)
#pp.plot(time, Xs)
pp.show()


print dA
print dB
print dC
print dD




