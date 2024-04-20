import os

def main():

    # Finding data values with each optimal
    results = open("data/Medianballoons.txt", "r")
    seq = results.readline()
    results.close()

    print("PARALLEL")
    print("------Median---------")
    os.system("java -cp bin MedianFilterParallel balloons.png balloons.jpg " + seq + " > data/PDataMedianB.txt")
    print("--Balloon--")
    print("--8%--")

    results = open("data/Medianspace.txt", "r")
    seq = results.readline()
    results.close()

    os.system("java -cp bin MedianFilterParallel space.jpg space.jpg " + seq + " > data/PDataMedianS.txt")
    print("--Space--")
    print("--17%--")

    results = open("data/Medianplanet.txt", "r")
    seq = results.readline()
    results.close()

    os.system("java -cp bin MedianFilterParallel planet.jpg planet.jpg " + seq + " > data/PDataMedianP.txt")
    print("--Planet--")
    print("--25%--")

    # Serial now
    print("SERIAL")
    print("------Median---------")
    os.system("java -cp bin MedianFilterSerial balloons.png balloons.jpg > data/SDataMedianB.txt")
    print("--Balloon--")
    print("--33%--")

    os.system("java -cp bin MedianFilterSerial space.jpg space.jpg > data/SDataMedianS.txt")
    print("--Space--")
    print("--41%--")

    os.system("java -cp bin MedianFilterSerial planet.jpg planet.jpg > data/SDataMedianP.txt")
    print("--Planet--")
    print("--50%--")

    # Back to parallel
    print("-----Mean------")
    results = open("data/Meanballoons.txt", "r")
    seq = results.readline()
    results.close()

    print("PARALLEL")
    os.system("java -cp bin MeanFilterParallel balloons.png balloons.jpg " + seq + " > data/PDataMeanB.txt")
    print("--Balloon--")
    print("--58%--")

    results = open("data/Meanspace.txt", "r")
    seq = results.readline()
    results.close()

    os.system("java -cp bin MeanFilterParallel space.jpg space.jpg " + seq + " > data/PDataMeanS.txt")
    print("--Space--")
    print("--66%--")

    results = open("data/Meanplanet.txt", "r")
    seq = results.readline()
    results.close()

    os.system("java -cp bin MeanFilterParallel planet.jpg planet.jpg " + seq + " > data/PDataMeanP.txt")
    print("--Planet--")
    print("--74%--")
    
    # Serial now
    print("SERIAL")
    print("------Mean---------")
    os.system("java -cp bin MeanFilterSerial balloons.png balloons.jpg > data/SDataMeanB.txt")
    print("--Balloon--")
    print("--82%--")

    os.system("java -cp bin MeanFilterSerial space.jpg space.jpg > data/SDataMeanS.txt")
    print("--Space--")
    print("--90%--")

    os.system("java -cp bin MeanFilterSerial planet.jpg planet.jpg > data/SDataMeanP.txt")
    print("--Planet--")
    print("--100%--")

if __name__=="__main__":
    main()