echo "Enter pattern"
read pattern
echo "Enter location"
read location
grep -lR "$pattern" "$location"
