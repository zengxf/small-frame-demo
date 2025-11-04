# -*- coding: utf-8 -*-
"""
强化学习

Created on Mon Sep  2 15:31:37 2019

@author: lzy
"""

import numpy as np
import random
import os
from matplotlib import pyplot as plt
def train(alpha=0.7,gamma=0.8):
    Q=np.zeros([37,37])
    R=np.full([37,37],-1)
    R[[0,34],[0]]=1000
    
    R[0,34]=0
    R[[1],[2,7]]=0
    R[[2],[1,3]]=0
    R[[3],[2,4]]=0
    R[[4],[3,5,10]]=0
    R[[5],[4,6]]=0
    R[[6],[5,12]]=0
    R[[7],[1,13]]=0
    R[[8],[14]]=0
    R[[9],[10,15]]=0
    R[[10],[9,4,16]]=0
    R[[11],[12]]=0
    R[[12],[6,11,18]]=0
    R[[13],[7,14,19]]=0
    R[[14],[8,13,20]]=0
    R[[15],[9,21]]=0
    R[[16],[10]]=0
    R[[17],[18,23]]=0
    R[[18],[12,17,24]]=0
    R[[19],[13,25]]=0
    R[[20],[14,21,26]]=0
    R[[21],[15,20,22]]=0
    R[[22],[21]]=0
    R[[23],[17,29]]=0
    R[[24],[18]]=0
    R[[25],[19,26,31]]=0
    R[[26],[20,25,27]]=0
    R[[27],[26,28]]=0
    R[[28],[27,34]]=0
    R[[29],[23,30]]=0
    R[[30],[29,36]]=0
    R[[31],[25,32]]=0
    R[[32],[31,33]]=0
    R[[33],[32]]=0
    R[[34],[28,35]]=0
    R[[35],[34,36]]=0
    R[[36],[35,30]]=0
        
    for state in range(1,37):
        recove_sta=state
        action_list=list(np.where(R[state]>=0)[0])
        action = random.choice(action_list)
        n=0
        while (1):
            Q[state,action]=(1-alpha)*Q[state,action]+alpha*(R[state,action]+gamma*max(Q[action]))
            state=action
#            print(state,n)
            if state==0:
                print('state:%d,n:%d'%(recove_sta,n))
                break
            action_list=list(np.where(R[state]>=0)[0])
            action = random.choice(action_list)
            n+=1
    np.savetxt('Q matrix.csv',Q,fmt='%f',delimiter=',')

def application(state,alpha=0.7,gamma=0.8):
    recov=[]
    recov.append(state)
    work_dir=os.getcwd()
    dir_list=os.listdir(work_dir)
    if 'Q matrix.csv' not in dir_list:
        train(alpha,gamma)
    Q=np.loadtxt('Q matrix.csv',delimiter=',')
    while (state!=0):
        action = np.argmax(Q[state])
        state=action
        recov.append(state)
    return recov

def plt_cont():
    plt.plot([0,3.3],[0,0],'k',[3.6,6],[0,0],'k')
    plt.plot([0,0],[0,6],'k')
    plt.plot([0,6],[6,6],'k')
    plt.plot([6,6],[0,6],'k')
    #行
    plt.plot([0,0.3],[5,5],'k',[0.6,3.3],[5,5],'k',[3.6,5.3],[5,5],'k',[5.6,6],[5,5],'k')
    plt.plot([0,0.3],[4,4],'k',[0.6,1.3],[4,4],'k',[1.6,2.3],[4,4],'k',[2.6,3.3],[4,4],'k',[3.6,5.3],[4,4],'k',[5.6,6],[4,4],'k')
    plt.plot([0,0.3],[3,3],'k',[0.6,1.3],[3,3],'k',[1.6,2.3],[3,3],'k',[2.6,4.3],[3,3],'k',[4.6,5.3],[3,3],'k',[5.6,6],[3,3],'k')
    plt.plot([0,0.3],[2,2],'k',[0.6,1.3],[2,2],'k',[1.6,4.3],[2,2],'k',[4.6,6],[2,2],'k')
    plt.plot([0,0.3],[1,1],'k',[0.6,3.3],[1,1],'k',[3.6,5.3],[1,1],'k',[5.6,6],[1,1],'k')
    #列
    plt.plot([1,1],[0,0.3],'k',[1,1],[0.6,1.3],'k',[1,1],[1.6,3.3],'k',[1,1],[3.6,5.3],'k',[1,1],[5.6,6],'k')
    plt.plot([2,2],[0,0.3],'k',[2,2],[0.6,1.3],'k',[2,2],[1.6,2.3],'k',[2,2],[2.6,5.3],'k',[2,2],[5.6,6],'k')
    plt.plot([3,3],[0,1.3],'k',[3,3],[1.6,2.3],'k',[3,3],[2.6,4.3],'k',[3,3],[4.6,5.3],'k',[3,3],[5.6,6],'k')
    plt.plot([4,4],[0,0.3],'k',[4,4],[0.6,5.3],'k',[4,4],[5.6,6],'k')
    plt.plot([5,5],[0,0.3],'k',[5,5],[0.6,1.3],'k',[5,5],[1.6,3.3],'k',[5,5],[3.6,4.3],'k',[5,5],[4.6,5.3],'k',[5,5],[5.6,6],'k')

def pt_recov(state):
    y,x=divmod(state,6)
    if x==0 and y==0:
        x=3.5
        y=-0.5   
    else:
        if x==0:
            x=5.5
            y=y-1
        else:
            x=x-0.5   
        y=5-y+0.5
        
    return x,y

def test(state):
    plt.figure(state)
    plt_cont()
    x_up,y_up=pt_recov(state)
    plt.plot(x_up,y_up,'og')
    
    res=application(state)
    for i in range(1,len(res)):
        x,y=pt_recov(res[i])
        plt.plot([x_up,x],[y_up,y],'r') 
        x_up=x
        y_up=y
               
if __name__=='__main__':
#    state=13
    alpha=0.9
    gamma=0.7
    for state in range(1,37): 
        test(state)
#        print("行走过程：",res)
    
        
       
        


