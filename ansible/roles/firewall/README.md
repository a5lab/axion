Role Name
=========

Uses firewalld on CentOS/Redhat 7 or Fedora 21/22 to configure the firewall

Requirements
------------

The ansible module firewalld is used for the configuration.

Role Variables
--------------

There are two hashes:
 - firewall_allow_services
 - firewall_allow_ports

Values for firewall_allow_services:

    firewall_allow_services:
      service: <service name>
      zone: [zone]			(default: public)
      permanent: [True|False]	(default: True)
      state: [enabled|disabled]	(default: enabled)

Only service is required!

Values for firewall_allow_ports:

    firewall_allow_ports:
      port: <port/protocol>
      zone: [zone]			(default: public)
      permanent: [True|False]	(default: True)
      state: [enabled|disabled]	(default: enabled)


Example Playbook
----------------

    - hosts: servers
      vars:
        firewall_allow_services:
          - { service: "http" }
          - { service: "telnet", zone: "dmz", permanent: True, state: "disabled" }
      roles:
        - marcelnijenhof.firewall

Disable firewall service example
---------------------------------

    - hosts: servers
      vars:
        firewall_allow_services:
          - { firewall_disable: true }
      roles:
        - firewall
