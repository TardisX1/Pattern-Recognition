#!/usr/bin/python
# coding=utf-8
#########################################
# kNN: k Nearest Neighbors

# 参数:        inX: vector to compare to existing dataset (1xN)
#             dataSet: size m data set of known vectors (NxM)
#             labels: data set labels (1xM vector)
#             k: number of neighbors to use for comparison

# 输出:     多数类
#########################################

from numpy import *
import operator
import os
 

# KNN分类核心方法
def kNNClassify(newInput, dataSet, labels, k):
    numSamples = dataSet.shape[0]  # shape[0]代表行数

    # # step 1: 计算欧式距离
    # tile(A, reps): 将A重复reps次来构造一个矩阵
    # the following copy numSamples rows for dataSet
    diff = tile(newInput, (numSamples, 1)) - dataSet  # Subtract element-wise
    squaredDiff = diff ** 2  # squared for the subtract
    squaredDist = sum(squaredDiff, axis = 1)   # sum is performed by row
    distance = squaredDist ** 0.5

    # # step 2: 对距离排序
    # argsort()返回排序后的索引
    sortedDistIndices = argsort(distance)

    classCount = {}  # 定义一个空的字典
    for i in xrange(k):
        # # step 3: 选择k个最小距离
        voteLabel = labels[sortedDistIndices[i]]

        # # step 4: 计算类别的出现次数
        # when the key voteLabel is not in dictionary classCount, get()
        # will return 0
        classCount[voteLabel] = classCount.get(voteLabel, 0) + 1

    # # step 5: 返回出现次数最多的类别作为分类结果
    maxCount = 0
    for key, value in classCount.items():
        if value > maxCount:
            maxCount = value
            maxIndex = key

    return maxIndex

# 将图片转换为向量
def  img2vector(filename):
    rows = 32
    cols = 32
    imgVector = zeros((1, rows * cols))
    fileIn = open(filename)
    for row in xrange(rows):
        lineStr = fileIn.readline()
        for col in xrange(cols):
            imgVector[0, row * 32 + col] = int(lineStr[col])

    return imgVector

# 加载数据集
def loadDataSet():
    # # step 1: 读取训练数据集
    print "---Getting training set..."
    dataSetDir = './digits/'
    trainingFileList = os.listdir(dataSetDir + 'trainingDigits')  # 加载训练数据
    numSamples = len(trainingFileList)

    train_x = zeros((numSamples, 1024))
    train_y = []
    for i in xrange(numSamples):
        filename = trainingFileList[i]

        # get train_x
        train_x[i, :] = img2vector(dataSetDir + 'trainingDigits/%s' % filename)

        # get label from file name such as "1_18.txt"
        label = int(filename.split('_')[0]) # return 1
        train_y.append(label)

    # # step 2:读取测试数据集
    print "---Getting testing set..."
    testingFileList = os.listdir(dataSetDir + 'testDigits') # load the testing set
    numSamples = len(testingFileList)
    test_x = zeros((numSamples, 1024))
    test_y = []
    for i in xrange(numSamples):
        filename = testingFileList[i]

        # get train_x
        test_x[i, :] = img2vector(dataSetDir + 'testDigits/%s' % filename)

        # get label from file name such as "1_18.txt"
        label = int(filename.split('_')[0]) # return 1
        test_y.append(label)

    return train_x, train_y, test_x, test_y

# 手写识别主流程
def testHandWritingClass():
    # # step 1: 加载数据
    print "step 1: load data..."
    train_x, train_y, test_x, test_y = loadDataSet()

    # # step 2: 模型训练.
    print "step 2: training..."
    pass

    # # step 3: 测试
    print "step 3: testing..."
    numTestSamples = test_x.shape[0]
    matchCount = 0
    for i in xrange(numTestSamples):
        predict = kNNClassify(test_x[i], train_x, train_y, 1)
        print "分类结果为: %d, 实际类别为: %d" % (predict, test_y[i])
        if predict == test_y[i]:
            matchCount += 1
    accuracy = float(matchCount) / numTestSamples

    # # step 4: 输出结果
    print "\nstep 4: show the result..."
    print "总测试样本数: %d" % numTestSamples  #输出测试总样本数
    print "测试正确样本数: %d" % matchCount    #输出测试正确样本数
    print '分类正确率: %.2f%%' % (accuracy * 100)  #输出正确率
    
#主程序入口
if __name__ == '__main__':
    testHandWritingClass()