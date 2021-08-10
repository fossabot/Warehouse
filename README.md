## Warehouse DSL

Warehouse is a lightweight Kotlin DSL dependency injection library this library has an extremely faster learning curve and
more human friendly logs unlike dagger and more explicit unlike koin it has graph nesting and multi-module support.

### Create

```kotlin
 val warehouse = warehouse {
    this add module {
        this add factory {
            this constructor { "Jon" }
            this creation CreationPattern.SINGLETON
            this injectsIn Name::class
        }

        this add factory {
            this constructor { Name(param()) }
        }
    }
}
```

### Use

```kotlin

private val name: Name by warehouse.inject()

```

## Install

Add it in your root build.gradle at the end of repositories:

```groove
allprojects {
	repositories {
		...
		maven {
            		url = uri("https://maven.pkg.github.com/osama-raddad/Warehouse")
            		credentials { //this is temporary solution until github fixes the public packages problem (this key is ready only)
                		username = "osama-raddad"
                		password = "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" 
           		}
       		}
	}
}
```

Step 2. Add the dependency

```groove
	dependencies {
	        implementation 'org.raddad:warehouse:1.1.0'
	}
```

## Contributing

We welcome contributions to Warehouse!

* ⇄ Pull requests and ★ Stars are always welcome.

### Let me know!

I’d be really happy if you sent me links to your projects where you use my library. Just send an email to
osama.s.raddad@gmail.com And do let me know if you have any questions or suggestion regarding the library.

## License

    Copyright 2021, Osama Raddad

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

