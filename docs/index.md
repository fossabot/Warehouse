## Welcome Warehouse DSL

Warehouse is a lightweight Kotlin DSL dependency injection library this library has an extremely faster learning and more human friendly logs unlike dagger 
and more explicit unlike koin it has graph nesting and multi-module support.

### Create

```kotlin
 val warehouse = warehouse {
    this add module {
        this add factory {
            this constructor { "Jon" }
            this creation CreationPattern.SINGLETON
            this injectsIn Name::class
        }
        
        this add factory{
            this constructor { Name(param()) }
        }
    }
}
```

### Use

```kotlin

 private val name: Name by warehouse.inject()
 
```


