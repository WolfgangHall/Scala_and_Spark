// check for a single even
def checkEven(num: Int): Boolean = {
    if (num % 2 == 0) {
        return true
    }
    return false
}
println(checkEven(10))
println(checkEven(9))


def checkEvenInList(nums: List[Int]): Boolean = {
    for (num <- nums){
        if (checkEven(num)){
            return true
        }
    }
    return false
}
println(checkEvenInList(List(1,2,3)))
println(checkEvenInList(List(1,3)))



def luckyNumberSeven(nums: List[Int]): Int = {
    var accum = 0
    for (num <- nums){
        if(num == 7){
            accum += 14
        } else {
            accum += num
        }
    }
    return accum
}
println(luckyNumberSeven(List(7, 1)))


def palindromeCheck(word: String): Boolean = {
    if (word.reverse == word) {
        return true
    } else {
        return false
    }
}
println(palindromeCheck("bob"))
println(palindromeCheck("ted"))
