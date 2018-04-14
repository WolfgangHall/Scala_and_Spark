// FUNCTIONS

// Unit is general data type
def simple(): Unit = {
  println("Simple print")
}
simple()


def adder(num1: Int, num2: Int) : Int = {
  return num1 + num2
}
adder(2, 3)


def greetName(name: String): String = {
  return s"Hello $name"
}
val fullgreet = greetName("Wolfgang")
println(fullgreet)


def isPrime(numToCheck: Int): Boolean = {
  for (n <- Range(2, numToCheck)){
    if(numToCheck % n == 0){
      return false
    }
  }
  return true
}

println(isPrime(10))
println(isPrime(23))


// pass in and return a list
val numbers = List(1, 2, 3, 4)

def check(nums: List[Int]): List[Int] = {
    return nums
}
println(check(numbers))
