import os

def main():
    # Need to find optimal sequential cutoff for each image, run 5 times each X 100 - 1000 inc 100

    # Finding optimum

    #Median
    print("------Median---------")
    os.system("java -cp bin MedianFilterParallel balloons.png balloons.jpg 5 > data/Medianballoons.txt")
    print("Balloons done")

    os.system("java -cp bin MedianFilterParallel space.jpg space.jpg 5 > data/Medianspace.txt")
    print("Space done")

    os.system("java -cp bin MedianFilterParallel planet.jpg planet.jpg 5 > data/Medianplanet.txt")
    print("Planet done")

    print("-----Mean------")
    # Mean
    os.system("java -cp bin MeanFilterParallel balloons.png balloons.jpg 5 > data/Meanballoons.txt")
    print("Balloons done")

    os.system("java -cp bin MeanFilterParallel space.jpg space.jpg 5 > data/Meanspace.txt")
    print("Space done")

    os.system("java -cp bin MeanFilterParallel planet.jpg planet.jpg 5 > data/Meanplanet.txt")
    print("Planet done")
    
    print("Optimal seqs found")


if __name__=="__main__":
    main()