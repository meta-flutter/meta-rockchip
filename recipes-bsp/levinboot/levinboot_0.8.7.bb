SUMMARY = "A bootloader project for (currently) the RK3399 SoC"
DESCRIPTION = "levinboot (name always lowercase) is a bootloader for (currently) RK3399 platforms with LPDDR4 memory"
AUTHOR = "dev@crystalgamma.de"
HOMEPAGE = "https://gitlab.com/DeltaGem/levinboot"
BUGTRACKER = "https://gitlab.com/DeltaGem/levinboot/-/issues"
SECTION = "bootloaders"

LICENSE = "CC0-1.0"
LIC_FILES_CHKSUM = "\
    file://${COREBASE}/meta/files/common-licenses/CC0-1.0;md5=0ceb3372c9595f0a8067e55da801e4a1 \
"

DEPENDS += "ninja-native"

SRC_URI = "git://gitlab.com/DeltaGem/levinboot.git;protocol=https;branch=release;name=levinboot \
           https://git.trustedfirmware.org/TF-A/trusted-firmware-a.git/snapshot/trusted-firmware-a-2.7.0.tar.gz;name=tfa;subdir=tfa"
SRCREV_levinboot = "2fbeba71d46929d5e6980911d482e65ad6fb17f1"
SRC_URI[tfa.md5sum] = "027614c144094b203e8e296ab7b076fe"
SRC_URI[tfa.sha256sum] = "553eeca87d4296cdf37361079d1a6446d4b36da16bc25feadd7e465537e7bd4d"

S = "${WORKDIR}/git"

do_configure() {
    ls -la ${WORKDIR}
    ls -la
    mkdir ${WORKDIR}/build && cd ${WORKDIR}/build
    ${S}/configure.py \
        --payload-lz4 \
        --payload-gzip \
        --payload-zstd \
        --payload-initcpio \
        --payload-sd \
        --payload-emmc \
        --payload-nvme \
        --payload-spi \
        --with-tf-a-headers ${WORKDIR}/tfa/trusted-firmware-a-2.7.0/include/export
}

do_compile() {
    cd ${WORKDIR}/build
    cc=${BUILD_CC} ninja
}

do_install() {
    cd ${WORKDIR}/build
    ls -laR
}

BBCLASSEXTEND = ""
