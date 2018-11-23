JFLAGS = -g
JC = javac
JVM= java
FILE=

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	keywordcounter.java \
	FibonacciHeap.java \
	Node.java

MAIN = keywordcounter

ifndef INPUT
INPUT = input.txt
endif

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) $(INPUT)


clean:
	$(RM) *.class
