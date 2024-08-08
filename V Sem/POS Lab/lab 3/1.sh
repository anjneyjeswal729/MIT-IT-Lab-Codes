file=$1
if [ -f "$file" ] ; then
	echo "$file is a file";
else
	if [ -d "$file" ] ; then
		echo "$file is a directory";
	else
		echo "invalid name" ;
	fi
fi
