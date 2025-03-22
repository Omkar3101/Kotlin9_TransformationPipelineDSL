import java.nio.channels.Pipe

//Project 9 : Transformation Pipeline DSL

//1. Create the Pipeline Class
class Pipeline<T>(private var data: List<T>) {
    //Applies a transformation to each element
    fun map(operation: (T) -> T): Pipeline<T> {
        data = data.map(operation)
        return this
    }
    //Filters elements based on a predicate
    fun filter(predicate: (T) -> Boolean): Pipeline<T> {
        data = data.filter(predicate)
        return this
    }

    //Optionally, add a reduce function to combine elements
    fun reduce(operation: (T,T) -> T): T{
        return data.reduce(operation)
    }

    //Retrieve the final transformed data
    fun result(): List<T> = data

}

//2. Build the DSL function
//The DSL function uses a lambda with receiver so you can map(), filter() directly.
fun <T> pipeline(data: List<T>, block: Pipeline<T>.()-> Unit): List<T> {
    val pipe = Pipeline(data)
    pipe.block() //Execute the DSL block on the Pipeline instance.
    return pipe.result()
}

//3. Test your DSL with a Sample List
fun main() {
    val numbers = listOf(1,2,3,4,5)

    // Using the DSL to transform the list
    val transformedNumbers = pipeline(numbers) {
        map {it * 2} //Double each number.
        filter {it > 5} //keep only numbers greater than 5.
    }
    println("Transformed Numbers: $transformedNumbers")
}