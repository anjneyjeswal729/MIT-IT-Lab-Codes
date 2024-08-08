echo "program started"
re=`find . -depth -name *.txt`

for i in $re
do
mv $i ${i%.txt}.text
done
