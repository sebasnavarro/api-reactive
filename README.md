# api-reactive

1) Creación de compilado:
  .\mvnw clean install

2) Ejecución de compilado:
  java -jar ./target/demo-0.0.1-SNAPSHOT.jar

3) Login a heroku cli:
  heroku login
  
4) Creación de app en heroku:
  heroku create api-reactivo
  
5) Configurando tu app-config.yaml:
  heroku stack:set container -a api-reactivo
  
6) Ingresando al container:
  heroku container:login

7) Creando imagen:
  docker image build . -f Dockerfile --tag registry.heroku.com/api-reactivo/web

8) Subiendo imagen:
  docker push registry.heroku.com/api-reactivo/web

9) Seleccionando contenedor a la app:
  heroku container:release web -a api-reactivo
  
  
 App desplegado en heroku:
 
 https://api-reactivo.herokuapp.com/
