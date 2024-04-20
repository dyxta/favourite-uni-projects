# Makefile for Assignment 1 PCP
# 9 August 2022
# Dylan Tasdhary

.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin
JAVA=/usr/bin/java
JAVAC=/usr/bin/javac

$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<
	
CLASSES2=MedianFilterSerial.class \
	 MeanFilterSerial.class \
	 MedianFilterParallel.class \
	 MeanFilterParallel.class
         
CLASSES=$(CLASSES2:%.class=$(BINDIR)/%.class)
	
default: $(CLASSES)

runmedser: $(CLASSES)
	$(JAVA) -cp $(BINDIR) MedianFilterSerial $(input) $(output) $(width)
	
runmenser: $(CLASSES)
	$(JAVA) -cp $(BINDIR) MeanFilterSerial $(input) $(output) $(width)

runmedpar: $(CLASSES)
	$(JAVA) -cp $(BINDIR) MedianFilterParallel $(input) $(output) $(width)

runmenpar: $(CLASSES)
	$(JAVA) -cp $(BINDIR) MeanFilterParallel $(input) $(output) $(width)
	
clean:
	rm $(BINDIR)/*.class