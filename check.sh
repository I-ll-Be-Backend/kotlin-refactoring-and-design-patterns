#!/bin/sh
# Compiles every step of every lesson in eclipse-temurin:21-jdk: the check each lesson passed before its video went out.
# Needs Docker. A step folder holding .nocheck does not compile on purpose and is skipped.
docker run --rm --entrypoint /bin/sh -v "$PWD:/w" eclipse-temurin:21-jdk -c '
curl -fsSL https://github.com/JetBrains/kotlin/releases/download/v2.1.0/kotlin-compiler-2.1.0.zip -o /tmp/k.zip && cd /opt && jar xf /tmp/k.zip && chmod +x /opt/kotlinc/bin/* && export PATH=/opt/kotlinc/bin:$PATH
cd /w; fail=0
for f in */step-*/*.kt; do
  [ -e "$f" ] || continue
  [ -e "$(dirname "$f")/.nocheck" ] && continue
  d=$(mktemp -d)
  if ( kotlinc $f -d $d -nowarn ) >/tmp/log 2>&1; then echo "ok    $f"; else echo "FAIL  $f"; cat /tmp/log; fail=1; fi
done
exit $fail'
