# api-reactive

1) Creación de compilado:
  .\mvnw clean install
  
  En caso de no funciona probar añadiendo lo siguiente: 
  
       <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.1.0</version>
          <configuration>
            <archive>
              <manifest>
                 <mainClass>com.lilblue.demo.DemoApplication</mainClass>
              </manifest>
            </archive>
           </configuration>
        </plugin>
  
  Luego ejecutar el siguiente comando:
  
  .\mvnw package spring-boot:repackage

2) Ejecución de compilado:
  java -jar ./target/demo-0.0.1-SNAPSHOT.jar

3) Login a heroku cli:
  heroku login
  
4) Creación de app en heroku:
  heroku create api-reactivo
  
5) Configurando tu app-config.yaml:
  heroku stack:set container -a api-reactive
  
6) Ingresando al container:
  heroku container:login

7) Creando imagen:
  docker image build . -f Dockerfile --tag registry.heroku.com/api-reactive/web

8) Subiendo imagen:
  docker push registry.heroku.com/api-reactive/web

9) Seleccionando contenedor a la app:
  heroku container:release web -a api-reactive
  
  
 App desplegado en heroku:
 
 https://api-reactive.herokuapp.com/swagger-ui.html
