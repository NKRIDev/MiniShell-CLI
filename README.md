# MiniShell Java CLI Framework

**MiniShell** is a lightweight and extensible command-line interface framework for Java applications. It provides a set of tools to simplify building interactive shells, handling files, making HTTP requests, managing colors, logging, and user input validation.

This project is structured in two main parts:

1.  **CLI Utility Library** – reusable helper classes for developers (`MiniCLI`, `FileCLI`, `APICLI`, `MiniColor`, etc.).

2.  **Custom Shell Creation** – build a fully interactive Java shell (`MiniShell`) with support for commands and environment variables.


----------

## Features

-   **Interactive CLI utilities**: prompts, validations, colored, logs, progress bars.

-   **File management**: create, read, list, and delete files easily with `FileCLI`.

-   **HTTP interactions**: asynchronous and synchronous GET/POST requests with headers and tokens using `APICLI`.

-   **Color & style management**: `MiniColor` supports colors, bold, italic, underline, and strikethrough.

-   **Custom shells**: define your own commands, environment variables, and shell behavior.

-   **Developer-friendly**: extend commands via `ShellCommand` interface or `AbstractShellCommand` base class.


----------

## Installation

You can add MiniShell-CLI to your project via **JitPack**.

### Ajouter le repository JitPack

**Gradle (Groovy DSL)** – add this in your `settings.gradle` :

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

**Gradle (Kotlin DSL)** – add this to your `settings.gradle.kts` file:
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

**Maven** – add this to the <repositories> section of your pom.xml:
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

### Add dependency
Replace Tag with the tag or version you want to use (v1.0, v1.2, etc.).

**Gradle (Groovy DSL)** – in `build.gradle` :
```gradle
dependencies {
    implementation 'com.github.NKRIDev:MiniShell-CLI:Tag'
}
```

**Gradle (Kotlin DSL)** – in `build.gradle.kts` :
```kotlin
dependencies {
    implementation("com.github.NKRIDev:MiniShell-CLI:Tag")
}
```

**Maven** – in the <dependencies> section of your pom.xml file:
```xml
<dependency>
    <groupId>com.github.NKRIDev</groupId>
    <artifactId>MiniShell-CLI</artifactId>
    <version>Tag</version>
</dependency>
```
----------

## CLI Utility Library

### MiniCLI

`MiniCLI` provides helpful methods for prompts, input validation, logs, and visual elements, etc.

**Example:**

```java
import fr.nkri.shell.cli.MiniCLI;  
import fr.nkri.shell.enums.MiniColor;  
import fr.nkri.shell.enums.LogLevel;  

final String name = MiniCLI.promptUser("Enter your name", "Invalid input!");  
MiniCLI.log("Hello " + name, LogLevel.SUCCESS);  
  
final boolean confirm = MiniCLI.promptYesNo("Do you want to continue?");  
final String choice = MiniCLI.promptChoice("Choose an option:", "Eat", "Sleep", "Code");
```

**Other Features:**

-   `promptEmail`, `promptFrPhone`, `promptURL`, etc.

-   `printTitle` – display stylized titles.

-   `log` – colored logging (INFO, SUCCESS, WARN, ERROR).


----------

### FileCLI

`FileCLI` simplifies file operations:

```java
import fr.nkri.shell.cli.FileCLI;  

// List folder content  
FileCLI.listFiles("./data");  
  
// Create a file  
FileCLI.createFile("./data/hello.txt", "Hello World!");  
  
// Read file content  
String content = FileCLI.readFile("./data/hello.txt");  
  
// Delete file  
FileCLI.deleteFile("./data/hello.txt");
```

----------

### APICLI

`APICLI` handles HTTP requests with optional token and custom headers.

**Example: GET request:**

```java
import fr.nkri.shell.cli.APICLI;  
  
final String response = APICLI.getSync("https://api.example.com/users", "myToken123", null);  
System.out.println(response);
```

**Example: POST request with JSON body:**

```java
final String jsonBody = "{\"name\":\"John\",\"age\":30}";  
final String result = APICLI.postSync("https://api.example.com/users", jsonBody, "myToken123", Map.of("Custom-Header", "value"));  
System.out.println(result);
```

----------

### MiniColor

Use `MiniColor` for colored and styled CLI output.

```java
System.out.println(MiniColor.color("Success!", MiniColor.GREEN));  
System.out.println(MiniColor.RED + MiniColor.BOLD + "Error!" + MiniColor.RESET);
```

### Progress Bar

`MiniCLI` provides a simple progress bar to visualize long-running tasks in the terminal.

**Example:**

```java
import fr.nkri.shell.cli.MiniCLI;   
import fr.nkri.shell.utils.ProgressBar;   
  
// Create a progress bar of 50 characters  
final ProgressBar bar = new ProgressBar(50);  
  
for(int i = 0; i <= 50; i++){

  // Update the progress
  bar.update(i); 
  
  //Simulate some work  
  try { 
      Thread.sleep(50);  
  } 
  catch (final InterruptedException  e) {  
      e.printStackTrace();  
  }  
}

System.out.println(MiniCLI.MiniColor.GREEN + "Loading complete!" + MiniCLI.MiniColor.RESET);
```
**Notes:**

-   The `ProgressBar` class allows specifying the total number of steps (characters) for the progress bar.

-   Updating the progress is done via `bar.update(currentStep)`.

-   Can be combined with loops or asynchronous tasks to indicate progress visually.
----------

## Creating a Custom Shell

Extend `MiniShell` to define your own shell and commands.

```java
import fr.nkri.shell.MiniShell;  
import fr.nkri.shell.cli.MiniCLI;

public class MyShell extends MiniShell {  
    
    public MyShell(final String shellName, final String prompt) { 
        super(shellName, prompt);
    }
    
    @Override  
    protected void welcome() {  
        MiniCLI.printTitle("Welcome to " + getShellName());
    }
    
    @Override
    protected void handleProcess(final String input) { 
        // Custom command handling (optional)
    }  
}
```
----------

### Creating Commands

Implement the `ShellCommand` interface or extend `AbstractShellCommand`:
```java
import fr.nkri.shell.cmds.ShellCommand;  
import fr.nkri.shell.cli.MiniCLI;  
import fr.nkri.shell.enums.MiniColor;  
  
public class GreetCommand implements ShellCommand {
    
    @Override  
    public String  getName() { return  "greet"; } 
    
    @Override  
    public String getDescription() { return  "Greets a user"; }
    
    @Override  
    public String getUsage() { return  "greet <name>"; }  
    
    @Override  
    public  void  action(final String[] args) { 
        String name;
        if(args.length  >  0) {  
            name = args[0];  
        } 
        else {  
            name = MiniCLI.promptUser("Enter your name", "Invalid input!");  
        } 
        
        System.out.println(MiniColor.color("Hello "  +  name  +  "!", MiniColor.BLUE));
    }  
}
```

----------

### Running Your Shell
```java
public class ConsoleMain {
    
    public static void main(final String[] args) {  
        final MyShell shell = new MyShell("CustomShell", ">"); 
        
        //Register commands
        shell.registerCommands(new  GreetCommand());
        
        //Register environment variable
        shell.registerEnvVar("USERNAME", "nkri"); 
        
        //Run shell
        shell.run();
    }  
}
```
**Example Usage:**

    ╔══════════════════╗  
    ║ Welcome to CustomShell ║  
    ╚══════════════════╝  
    CustomShell> greet  
    Enter your name: nkri  
    Hello nkri!  
    CustomShell> exit

----------

## Developer Notes

-   **Library (`fr.nkri.shell.cli`)**: utilities for CLI applications (prompts, file management, HTTP requests, colors, progress bars, etc.).

-   **Custom Shell (`MiniShell`)**: interactive shells for applications, supporting commands, environment variables, and runtime extension.

----------

## License

**MIT License (`LICENSE-MIT.txt`)** – Standard MIT license.

**Commercial License (`LICENSE-COMMERCIAL.txt`)**

**Author** : NKRI

**Discord:** :  https://discord.gg/dkxWh58TqP

**WebSite** : https://portfolio-kilyannb.vercel.app/