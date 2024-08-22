
n=$#

if [ "$n" -eq 0 ]; then
    echo "No files input"
    exit 1
fi

for f in "$@"; do
    rm -i $f
done
