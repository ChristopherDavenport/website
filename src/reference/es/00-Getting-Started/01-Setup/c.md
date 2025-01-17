---
out: Installing-sbt-on-Linux.html
---

  [ZIP]: $sbt_native_package_base$/sbt-$app_version$.zip
  [TGZ]: $sbt_native_package_base$/sbt-$app_version$.tgz
  [RPM]: $sbt_rpm_package_base$sbt-$app_version$.rpm
  [DEB]: $sbt_deb_package_base$sbt-$app_version$.deb
  [Manual-Installation]: Manual-Installation.html
  [website127]: https://github.com/sbt/website/issues/127
  [cert-bug]: https://bugs.launchpad.net/ubuntu/+source/ca-certificates-java/+bug/1739631

Installing sbt on Linux
-----------------------

### Instalar desde un paquete universal

Descarga el paquete [ZIP][ZIP] o [TGZ][TGZ] y descomprímelo.

### Instalar JDK

Primero desberás de instalar JDK. Recomendamos Oracle JDK 8 u OpenJDK 8.
Los detalles sobre el nombre de los paquetes cambian de una distribución a otra.

Por ejemplo, Ubuntu xenial (16.04LTS) usa
[openjdk-8-jdk](https://packages.ubuntu.com/hu/xenial/openjdk-8-jdk).

La familia Redhat lo llama
[java-1.8.0-openjdk-devel](https://apps.fedoraproject.org/packages/java-1.8.0-openjdk-devel).

### Ubuntu y otras distribuciones basadas en Debian

Los paquetes [DEB][DEB] son oficialmente soportados por sbt.

Ubuntu y otras distribuciones basadas en Debian usan el formato DEB, pero por lo
general no necesitas instalar software desde un fichero DEB local.
En su lugar lo que se utiliza son los gestores de paquetes, tanto desde la línea de
comandos (p.e. `apt-get`, `aptitude`) o con una interfaz gráfica de usuario
(p.e. Synaptic).
Ejecuta lo siguiente desde el terminal para instalar `sbt`
(necesitarás tener privilegios de administrador para hacerlo, de ahí el `sudo`).

    echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
    sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
    sudo apt-get update
    sudo apt-get install sbt

Los gestores de paquetes utilizan los repositorios para buscar los paquetes que
se desean instalar. Los binarios de sbt son publicados en Bintray y
convenientemente Bintray proporciona un repositorio APT.
Sólo tienes que añadir el repositorio en aquellos ficheros utilizados por tu
gestor de paquetes.

Una vez `sbt` haya sido instalado podrás gestionar el paquete en `aptitude` o
Synaptic después de que hayas actualizado la caché de paquetes. También podrás
ver el repositorio recién añadido al final de la lista en
Preferencias del sistema -> Software y actualizaciones -> Otro software:

![Ubuntu Software & Updates Screenshot](../files/ubuntu-sources.png "Ubuntu Software & Updates Screenshot")

**Nota**: Se han reportado errores de SSL en Ubuntu: `Server access Error:
java.lang.RuntimeException: Unexpected error:
java.security.InvalidAlgorithmParameterException: the trustAnchors parameter
must be non-empty url=https://repo1.maven.org/maven2/org/scala-sbt/sbt/1.1.0/sbt-1.1.0.pom`,
los cuales aparentemente impiden a OpenJDK 9 utilizar el formato PKCS12 para
`/etc/ssl/certs/java/cacerts` [cert-bug][cert-bug].
Según <https://stackoverflow.com/a/50103533/3827> esto ha sido arreglado en
Ubuntu Cosmic (18.10) aunque Ubuntu Bionic LTS (18.04) aún sigue esperando una
release. Mira las respuesta para encontrar soluciones.

### Red Hat Enterprise Linux y otras distribuciones basadas en RPM

Los paquetes [RPM][RPM] son oficialmente soportados por sbt.

Red Hat Enterprise Linux y otras distribuciones basadas en RPM utilizan el
formato RPM. Ejecuta lo siguiente desde el terminal para instalar `sbt`
(necesitarás tener privilegios de administrador para hacerlo, de ahí el `sudo`).

    curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo
    sudo yum install sbt

Los binarios de sbt son publicados en Bintray y convenientemente Bintray
proporciona un repositorio RPM. Sólo tienes que añadir el repositorio en
aquellos ficheros utilizados por tu gestor de paquetes.

En Fedora, `sbt 0.13.1` está [disponible desde repos oficiales](https://fedora.pkgs.org/28/fedora-i386/sbt-0.13.1-9.fc28.1.noarch.rpm.html).
Si quieres instalar `sbt 1.1.6` o superior tendrás que desinstalar `sbt 0.13`
(si es que está instaladoo) e indicar que quieres instalar las nuevas versiones
de `sbt` (p.e. `sbt 1.1.6` o superior) utilizando `bintray-sbt-rpm.repo`.

    sudo dnf remove sbt # desinstalar sbt si sbt 0.13 estaba instalado (puede que no sea necesario)
    sudo dnf --enablerepo=bintray--sbt-rpm install sbt

> **Nota:** Por favor, reporta cualquier problema con estos paquetes al proyecto
> [sbt](https://github.com/sbt/sbt)

### Gentoo

El árbol oficial contiene ebuilds para sbt. Para instalar la última versión
disponible escribe:

    emerge dev-java/sbt
