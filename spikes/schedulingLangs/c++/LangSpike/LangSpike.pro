TEMPLATE = app
CONFIG += console c++11
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += main.cpp \
    courseSection.cpp \
    allsections.cpp \
    cohortrequirements.cpp \
    cohorts.cpp \
    schedule.cpp \
    permutations.cpp \
    enrollment.cpp

HEADERS += \
    courseSection.h \
    allsections.h \
    cohortrequirements.h \
    cohorts.h \
    schedule.h \
    permutation.h \
    enrollment.h
