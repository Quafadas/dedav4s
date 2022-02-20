FROM gitpod/workspace-full

RUN brew install scala

RUN brew install sbt

RUN sudo sh -c '(echo "#!/usr/bin/env sh" && curl -L https://github.com/lihaoyi/Ammonite/releases/download/2.5.2/3.0-2.5.2) > /usr/local/bin/amm && chmod +x /usr/local/bin/amm'