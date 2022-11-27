# Production deploy
Commands to production deploy:
* ansible-playbook -v --diff --inventory inventory/production main.yml

# Development deploy for CentoOS8
Commands to development deploy:
* ansible-playbook -v --diff --inventory inventory/development playbooks/create_docker_env.yml
* ansible-playbook -v --diff --inventory inventory/development main.yml
* ansible-playbook -v --diff --inventory inventory/development playbooks/clean_docker_env.yml

# Known problems
## Unable to gather facts on ubuntu
Command to install packages to gather facts:
* sudo apt-get install dmidecode

## Unable to install latest version
Commands to install latest version:
* apt-get update
* apt-get install software-properties-common
* apt-add-repository ppa:ansible/ansible
* apt-get update
* apt-get install ansible
* ansible-galaxy collection install community.general
