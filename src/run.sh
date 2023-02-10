echo podaj username:
read username
echo podaj hasło:
read password

export PGPASSWORD="$password"
psql -d postgres -U $username -f tabele.sql

javac --release 8 -cp ".;postgresql-42.5.3.jar" skijumping/*.java
java -cp ".;postgresql-42.5.3.jar" skijumping/Main $username $password