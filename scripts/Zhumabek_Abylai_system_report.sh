#!/bin/bash
LOG="logs/Zhumabek_Abylai_system_report.log"
{
echo "Student: Zhumabek Abylai"
echo "Date: $(date)"
echo "User: $(whoami)"
echo "OS: $(grep PRETTY_NAME /etc/os-release)"
echo "Kernel: $(uname -r)"
echo "--- CPU ---"
lscpu | grep "Model name"
echo "--- Memory ---"
free -h
echo "--- Disk ---"
df -h
} | tee -a "$LOG"
