echo podaj username:
read username
echo podaj hasło:
read password

PGPASSWORD = iks
psql -d postgres -U postgres -f tabele.sql