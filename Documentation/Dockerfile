FROM mariadb:latest

# Variables de entorno para configurar el usuario y la contraseña de MariaDB
ENV MYSQL_ROOT_PASSWORD=admin
ENV MYSQL_DATABASE=ticketDB
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=admin

# Copiar el script de entrada desde la carpeta ER en el mismo directorio que el Dockerfile
COPY ./ER/ticketSysScript.sql /docker-entrypoint-initdb.d/

# Cambiar permisos del script para que sea ejecutable
RUN chmod +r /docker-entrypoint-initdb.d/ticketSysScript.sql

# Opcional: Exponer el puerto de MariaDB
EXPOSE 3306
