import os;
def main():
    print("Balloons....")
    os.system("java -cp bin MedianFilterSerial balloons.png balloons.jpg 5")
    os.system("java -cp bin MeanFilterSerial balloons.png balloons.jpg 5")
    os.system("java -cp bin MedianFilterParallel balloons.png balloons.jpg 5")
    os.system("java -cp bin MeanFilterParallel balloons.png balloons.jpg 5")
    print("Done")

    print("Space")
    os.system("java -cp bin MedianFilterSerial space.jpg space.jpg 5")
    os.system("java -cp bin MeanFilterSerial space.jpg space.jpg 5")
    os.system("java -cp bin MedianFilterParallel space.jpg space.jpg 5")
    os.system("java -cp bin MeanFilterParallel space.jpg space.jpg 5")
    print("Done")

    print("Planet")
    os.system("java -cp bin MedianFilterSerial planet.jpg planet.jpg 5")
    os.system("java -cp bin MeanFilterSerial planet.jpg planet.jpg 5")
    os.system("java -cp bin MedianFilterParallel planet.jpg planet.jpg 5")
    os.system("java -cp bin MeanFilterParallel planet.jpg planet.jpg 5")
    print("Done")

    print("Woman")
    os.system("java -cp bin MedianFilterSerial woman.png woman.jpg 5")
    os.system("java -cp bin MeanFilterSerial woman.png woman.jpg 5")
    os.system("java -cp bin MedianFilterParallel woman.png woman.jpg 5")
    os.system("java -cp bin MeanFilterParallel woman.png woman.jpg 5")
    print("Done")

    print("Chaplin")
    os.system("java -cp bin MedianFilterSerial chaplin.png chaplin.jpg 5")
    os.system("java -cp bin MeanFilterSerial chaplin.png chaplin.jpg 5")
    os.system("java -cp bin MedianFilterParallel chaplin.png chaplin.jpg 5")
    os.system("java -cp bin MeanFilterParallel chaplin.png chaplin.jpg 5")
    print("Complete")

def check():
    os.system("javac ImageChecker.java")
    os.system("java ImageChecker space.jpg Mean")
    os.system("java ImageChecker planet.jpg Mean")
    os.system("java ImageChecker balloons.jpg Mean")

    os.system("java ImageChecker space.jpg Median")
    os.system("java ImageChecker planet.jpg Median")
    os.system("java ImageChecker balloons.jpg Median")

if __name__ == "__main__":
    check()