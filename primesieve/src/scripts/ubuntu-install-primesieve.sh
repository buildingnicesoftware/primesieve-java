sudo apt -y install g++ cmake
mkdir tmp
cd tmp
git clone https://github.com/kimwalisch/primesieve.git
cd primesieve
cmake .
cmake --build . --parallel
sudo ldconfig
sudo mkdir -p /usr/local/lib
sudo cp libprimesieve.* /usr/lib/.
sudo cp -R include/primesieve* /usr/local/include/.
cd ../..
echo "primesieve build complete"
ls -l /usr/local/include/primesieve*
ls -l /usr/lib/libprimesieve.*