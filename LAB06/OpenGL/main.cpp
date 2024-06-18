/* // TASK 1 - black base window hello world
#include <GLFW/glfw3.h>

int main(void)
{
    GLFWwindow* window;

if (!glfwInit())
return -1;

window = glfwCreateWindow(640, 480, "Hello World", NULL, NULL);
if (!window)
{
    glfwTerminate();
    return -1;
}

glfwMakeContextCurrent(window);

while (!glfwWindowShouldClose(window))
{
    glClear(GL_COLOR_BUFFER_BIT);

    glfwSwapBuffers(window);

    glfwPollEvents();
}

glfwTerminate();
return 0;
*/

/* // TASK 2 - another color background
#include <glad/glad.h>
#include <GLFW/glfw3.h>
#include <iostream>

// Funkcja, która bêdzie wywo³ywana przy zmianie rozmiaru okna
void framebuffer_size_callback(GLFWwindow* window, int width, int height)
{
    glViewport(0, 0, width, height);
}

int main(void)
{
    // Inicjalizacja GLFW
    if (!glfwInit())
    {
        std::cout << "Failed to initialize GLFW" << std::endl;
        return -1;
    }

    // Ustawienie wersji OpenGL na 3.3
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    // Tworzenie okna
    GLFWwindow* window = glfwCreateWindow(640, 480, "Hello World", NULL, NULL);
    if (!window)
    {
        std::cout << "Failed to create GLFW window" << std::endl;
        glfwTerminate();
        return -1;
    }
    glfwMakeContextCurrent(window);

    // Inicjalizacja GLAD
    if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress))
    {
        std::cout << "Failed to initialize GLAD" << std::endl;
        return -1;
    }

    // Ustawienie funkcji wywo³ywanej przy zmianie rozmiaru okna
    glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);

    // Ustawienie koloru t³a (ciemnozielononiebieski)
    glClearColor(0.2f, 0.3f, 0.3f, 1.0f);

    // G³ówna pêtla renderowania
    while (!glfwWindowShouldClose(window))
    {
        // Czyszczenie bufora koloru
        glClear(GL_COLOR_BUFFER_BIT);

        // Zamiana buforów przedniego i tylnego
        glfwSwapBuffers(window);

        // Obs³uga zdarzeñ
        glfwPollEvents();
    }

    // Zwalnianie zasobów
    glfwTerminate();
    return 0;
}*/

/* // TASK 3 - Triangle

#include <glad/glad.h>
#include <GLFW/glfw3.h>
#include <iostream>

// Funkcja, która bêdzie wywo³ywana przy zmianie rozmiaru okna
void framebuffer_size_callback(GLFWwindow* window, int width, int height)
{
    glViewport(0, 0, width, height);
}

// Funkcja do obs³ugi wejœcia
void processInput(GLFWwindow* window)
{
    if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
        glfwSetWindowShouldClose(window, true);
}

// Kod vertex shader
const char* vertexShaderSource = "#version 330 core\n"
"layout (location = 0) in vec3 aPos;\n"
"void main()\n"
"{\n"
"   gl_Position = vec4(aPos, 1.0);\n"
"}\0";

// Kod fragment shader
const char* fragmentShaderSource = "#version 330 core\n"
"out vec4 FragColor;\n"
"void main()\n"
"{\n"
"   FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n"
"}\0";

int main()
{
    // Inicjalizacja GLFW
    if (!glfwInit())
    {
        std::cout << "Failed to initialize GLFW" << std::endl;
        return -1;
    }

    // Ustawienie wersji OpenGL na 3.3
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    // Tworzenie okna
    GLFWwindow* window = glfwCreateWindow(800, 600, "LearnOpenGL", NULL, NULL);
    if (!window)
    {
        std::cout << "Failed to create GLFW window" << std::endl;
        glfwTerminate();
        return -1;
    }
    glfwMakeContextCurrent(window);

    // Inicjalizacja GLAD
    if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress))
    {
        std::cout << "Failed to initialize GLAD" << std::endl;
        return -1;
    }

    // Ustawienie funkcji wywo³ywanej przy zmianie rozmiaru okna
    glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);

    // Kompilacja vertex shader
    unsigned int vertexShader = glCreateShader(GL_VERTEX_SHADER);
    glShaderSource(vertexShader, 1, &vertexShaderSource, NULL);
    glCompileShader(vertexShader);
    int success;
    char infoLog[512];
    glGetShaderiv(vertexShader, GL_COMPILE_STATUS, &success);
    if (!success)
    {
        glGetShaderInfoLog(vertexShader, 512, NULL, infoLog);
        std::cout << "ERROR::SHADER::VERTEX::COMPILATION_FAILED\n" << infoLog << std::endl;
    }

    // Kompilacja fragment shader
    unsigned int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(fragmentShader, 1, &fragmentShaderSource, NULL);
    glCompileShader(fragmentShader);
    glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, &success);
    if (!success)
    {
        glGetShaderInfoLog(fragmentShader, 512, NULL, infoLog);
        std::cout << "ERROR::SHADER::FRAGMENT::COMPILATION_FAILED\n" << infoLog << std::endl;
    }

    // Linkowanie shaderów
    unsigned int shaderProgram = glCreateProgram();
    glAttachShader(shaderProgram, vertexShader);
    glAttachShader(shaderProgram, fragmentShader);
    glLinkProgram(shaderProgram);
    glGetProgramiv(shaderProgram, GL_LINK_STATUS, &success);
    if (!success)
    {
        glGetProgramInfoLog(shaderProgram, 512, NULL, infoLog);
        std::cout << "ERROR::SHADER::PROGRAM::LINKING_FAILED\n" << infoLog << std::endl;
    }
    glDeleteShader(vertexShader);
    glDeleteShader(fragmentShader);

    // Definicja wierzcho³ków trójk¹ta
    float vertices[] = {
        -0.5f, -0.5f, 0.0f,
         0.5f, -0.5f, 0.0f,
         0.0f,  0.5f, 0.0f
    };

    // Tworzenie VAO i VBO
    unsigned int VBO, VAO;
    glGenVertexArrays(1, &VAO);
    glGenBuffers(1, &VBO);

    // Bindowanie VAO
    glBindVertexArray(VAO);

    // Kopiowanie danych wierzcho³ków do VBO
    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);

    // Ustawienie atrybutów wierzcho³ków
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(float), (void*)0);
    glEnableVertexAttribArray(0);

    // Odwi¹zanie VBO i VAO
    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindVertexArray(0);

    // Ustawienie koloru t³a (ciemnozielononiebieski)
    glClearColor(0.2f, 0.3f, 0.3f, 1.0f);

    // G³ówna pêtla renderowania
    while (!glfwWindowShouldClose(window))
    {
        // Obs³uga wejœcia
        processInput(window);

        // Czyszczenie bufora koloru
        glClear(GL_COLOR_BUFFER_BIT);

        // Renderowanie trójk¹ta
        glUseProgram(shaderProgram);
        glBindVertexArray(VAO);
        glDrawArrays(GL_TRIANGLES, 0, 3);

        // Zamiana buforów przedniego i tylnego
        glfwSwapBuffers(window);

        // Obs³uga zdarzeñ
        glfwPollEvents();
    }

    // Zwalnianie zasobów
    glDeleteVertexArrays(1, &VAO);
    glDeleteBuffers(1, &VBO);
    glfwTerminate();
    return 0;
}
*/

 // TASK 4 - Shaders

#include <glad/glad.h>
#include <GLFW/glfw3.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>

// Funkcja do kompilacji shaderów
unsigned int compileShader(unsigned int type, const std::string& source) {
    unsigned int id = glCreateShader(type);
    const char* src = source.c_str();
    glShaderSource(id, 1, &src, nullptr);
    glCompileShader(id);

    int result;
    glGetShaderiv(id, GL_COMPILE_STATUS, &result);
    if (result == GL_FALSE) {
        int length;
        glGetShaderiv(id, GL_INFO_LOG_LENGTH, &length);
        char* message = new char[length];
        glGetShaderInfoLog(id, length, &length, message);
        std::cout << "Failed to compile shader!" << std::endl;
        std::cout << message << std::endl;
        delete[] message;
        glDeleteShader(id);
        return 0;
    }

    return id;
}

// Funkcja do tworzenia programu shaderów
unsigned int createShader(const std::string& vertexShader, const std::string& fragmentShader) {
    unsigned int program = glCreateProgram();
    unsigned int vs = compileShader(GL_VERTEX_SHADER, vertexShader);
    unsigned int fs = compileShader(GL_FRAGMENT_SHADER, fragmentShader);

    glAttachShader(program, vs);
    glAttachShader(program, fs);
    glLinkProgram(program);
    glValidateProgram(program);

    glDeleteShader(vs);
    glDeleteShader(fs);

    return program;
}

// Funkcja do ³adowania plików shaderów
std::string loadShaderFromFile(const char* filepath) {
    std::ifstream file(filepath);
    std::stringstream buffer;
    buffer << file.rdbuf();
    return buffer.str();
}

// Funkcja callback do zmiany rozmiaru okna
void framebuffer_size_callback(GLFWwindow* window, int width, int height)
{
    glViewport(0, 0, width, height);
}

// Funkcja do obs³ugi wejœcia
void processInput(GLFWwindow* window)
{
    if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS)
        glfwSetWindowShouldClose(window, true);
}

int main() {
    glfwInit();
    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

    GLFWwindow* window = glfwCreateWindow(800, 600, "LearnOpenGL", NULL, NULL);
    if (window == NULL) {
        std::cout << "Failed to create GLFW window" << std::endl;
        glfwTerminate();
        return -1;
    }
    glfwMakeContextCurrent(window);
    glfwSetFramebufferSizeCallback(window, framebuffer_size_callback);

    if (!gladLoadGLLoader((GLADloadproc)glfwGetProcAddress)) {
        std::cout << "Failed to initialize GLAD" << std::endl;
        return -1;
    }

    // £adowanie plików shaderów
    std::string vertexShaderSource = loadShaderFromFile("vertexShader.vs");
    std::string fragmentShaderSource = loadShaderFromFile("fragmentShader.fs");

    // Tworzenie programu shaderów
    unsigned int shaderProgram = createShader(vertexShaderSource, fragmentShaderSource);

    // Definicja wierzcho³ków trójk¹ta
    float vertices[] = {
        // pozycje          // kolory
         0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f,   // prawy dolny
        -0.5f, -0.5f, 0.0f,  0.0f, 1.0f, 0.0f,   // lewy dolny
         0.0f,  0.5f, 0.0f,  0.0f, 0.0f, 1.0f    // górny
    };

    unsigned int VBO, VAO;
    glGenVertexArrays(1, &VAO);
    glGenBuffers(1, &VBO);

    glBindVertexArray(VAO);

    glBindBuffer(GL_ARRAY_BUFFER, VBO);
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);

    // Atrybut pozycji
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 6 * sizeof(float), (void*)0);
    glEnableVertexAttribArray(0);
    // Atrybut koloru
    glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 6 * sizeof(float), (void*)(3 * sizeof(float)));
    glEnableVertexAttribArray(1);

    glBindBuffer(GL_ARRAY_BUFFER, 0);
    glBindVertexArray(0);

    glUseProgram(shaderProgram);

    // Pêtla renderowania
    while (!glfwWindowShouldClose(window)) {
        processInput(window);

        glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);

        glUseProgram(shaderProgram);
        glBindVertexArray(VAO);
        glDrawArrays(GL_TRIANGLES, 0, 3);

        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    glDeleteVertexArrays(1, &VAO);
    glDeleteBuffers(1, &VBO);

    glfwTerminate();
    return 0;
}

/*
Wyjaœnienie
Vertex Shader: Przetwarza dane wierzcho³ków, przekszta³caj¹c ich pozycje i przekazuj¹c kolor do fragment shadera.
Fragment Shader: Odbiera kolor od vertex shadera i ustawia kolor fragmentu.
Uniforms: Zmienna globalna u¿ywana do przekazywania danych z aplikacji do shadera. Przyk³adem jest zmienna ourColor, 
która mo¿e byæ u¿ywana do dynamicznej zmiany koloru trójk¹ta.
GLSL: Jêzyk shaderów podobny do C, u¿ywany do programowania shaderów.
Ten kod ilustruje, jak u¿ywaæ shaderów do renderowania kolorowego trójk¹ta w OpenGL. Aby uruchomiæ ten kod, nale¿y 
zapisaæ kody shaderów do plików vertexShader.vs i fragmentShader.fs, a nastêpnie skompilowaæ i uruchomiæ program C++.
*/