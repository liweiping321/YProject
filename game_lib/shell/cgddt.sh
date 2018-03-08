#!/bin/bash
#Author: tomas.chen 
#Date: 2015-11-23
#Versions: 1.0 
#Update_time: 2015-11-24
#Versions: 1.0.0

Showmsg() {
        Errorstate="$?"
        Showtype="$1"
        Showcontent="$2"
            case "${Showtype}" in
               errorsys|Errorsys|ERRORSYS)
                   if [ "${Errorstate}" -ne 0 ];then
                     echo -e "\n[`date +%F\ %T`] \033[31m Error: ${Showcontent} \033[0m" |tee -a ${Log_file}
                     exit 1
                   fi
                   ;;
               errorusermsg|Errorusermsg|ERRORUSERMSG)
                   echo -e "\n[`date +%F\ %T`] \033[31m Error: ${Showcontent} \033[0m" |tee -a ${Log_file}
                   exit 1
                   ;;
               msg1|Msg1|MSG1)
                   echo -e "[`date +%F\ %T`]  ${Showcontent}" >>${Log_file}
                   ;;
               msg2|Msg2|MSG2)
                   echo -e -n "[`date +%F\ %T`]  ${Showcontent}" |tee -a ${Log_file}
                   ;;
               ok|Ok|OK)
                   echo -e "\033[32m OK \033[0m" |tee -a ${Log_file}
                   ;;
               *)
                   echo -e "\n\n[`date +%F\ %T`] \033[31m Error: Call founction showMsg error\033[0m" |tee -a ${Log_file}
                   ;;
        esac
}

###Remote run commands $Error_exit($3)不为1 ssh错误退出;为1 ssh 错误继续。
Auto_ssh(){
        Src_host="$1"
        Src_port="16333"
        Commands="$2"
        Timeout="$3"
        Error_exit="$4"
        expect -c "
                set timeout ${Timeout}
                spawn  ssh -p $Src_port $Src_host \"$Commands\"
                expect {
                          \"(yes/no)?\"                         {send \"yes\r\";exp_continue}
                          \"password:\"                         {send \"${Password}\r\";exp_continue}
                          \"FATAL\"                             {exit 2;exp_continue}
                          \"timeout\"                           {exit 2;exp_continue}
                          \"No route to host\"                  {exit 2;exp_continue}
                          \"Connection Refused\"                {exit 2;exp_continue}
                          \"Connection refused\"                {exit 2;exp_continue}
                          \"Host key verification failed\"      {exit 2;exp_continue}
                          \"Illegal host key\"                  {exit 2;exp_continue}
                          \"Connection Timed Out\"              {exit 2;exp_continue}
                          \"Connection timed out\"              {exit 2;exp_continue}
                          \"Interrupted system call\"           {exit 2;exp_continue}
                          \"Disconnected; connection lost\"     {exit 2;exp_continue}
                          \"Authentication failed\"             {exit 2;exp_continue}
                          \"File exists\"                       {exit 2;exp_continue}
                          \"Error:\"                            {exit 2;exp_continue}
                          \"ERROR:\"                            {exit 2;exp_continue}
                          \"error:\"                            {exit 2;exp_continue}
                          \"No such file\"                      {exit 2;exp_continue}
                          \"Permission denied\"                 {exit 2;exp_continue}
                          \"Destination Unreachable\"           {exit 2}
                       }

        " > ${The_file_dir}/ssh.log
        if [ "$?" -ne 0 ]; then
                if [ "${Error_exit}" != "1" ]; then
                        Showmsg errorusermsg "some error occur when autoRun '${Commands}' on ${SRC_HOST}"
                fi
        fi
}

Alarm_Control(){
    sed -i "/alarm/d" /root/config
    echo "alarm:$1" >>/root/config
}

### display colorful string
Color_str(){
    [ $# -ne 2   ] && {
        echo "Illegal function call"
        echo "$FUNCNAME [red|green|blue|yello|pink] str"
        exit 1
    }

    case "$1" in
        red)
            echo -e "\033[31m$2\033[0m"
            ;;
        green)
            echo -e "\033[32m$2\033[0m"
            ;;
        blue)
            echo -e "\033[33m$2\033[0m"
            ;;
        yello)
            echo -e "\033[34m$2\033[0m"
            ;;
        pink)
            echo -e "\033[35m$2\033[0m"
            ;;
        *)
            echo "$2"
            ;;
    esac
}

### install 函数
####check the need file is exsit
Check_file_exist(){
        while [ "$1" ]; do
                if [ ! -e "$1" ]; then
                       Showmsg "errorusermsg" "the file $1 is not exist"
                fi
                shift
        done
}

Rm_exist_file(){
        while [ "$1" ]; do
                if [ -e "$1" ]; then
                       rm -rf $1
                fi
                shift
        done
}

###wget file function
Wget_file(){
        while [ "$1" ]; do
                wget -c --http-user=${Http_user} --http-passwd=${Http_password} http://${Url_ip}:9988/cgddt/$1 >>${Log_file} 2>&1
                shift
        done
}

###rsync file function
Rsync_file(){
Option="$1"
Src_user="$2"
Src_ip="$3"
Src="$4"
Dest="$5"
Src_password="$6"
/usr/bin/expect << EOF
spawn rsync -azvcP --delete -e "ssh -p 16333" ${Option} ${Src_user}@${Src_ip}:$Src $Dest
set timeout -1
         expect {
                -timeout 60
                "yes/no"    {send "yes\r";exp_continue}
                "*password" {send "${Src_password}\r"}
               }
         expect eof
EOF
}

###add 2015-12-15 by jinliang

Check_network() {
    Showmsg "msg2" "install check ......"
    rm -f ~/.ssh/known_hosts
    local Game_num=`netstat -ntup |grep ESTA|grep -E ':9200 |:9300 '|wc -l`
    if [ "$Game_num" != 0 ];then
            Showmsg "errorusermsg" "the webserver is Online  $Game_num"
    fi
    local Process_num=`ps -ef|grep -E "CenterServer|GameServer|FightingServer|RankServer"|grep -v grep |wc -l`
    if [ "$Process_num" != 0 ];then
            Showmsg "errorusermsg" "the game progress $Process_num"
    fi
    ###七道自有资产添加内网ip
        innerIP=`getLocalInnerIP`
        if [ -z "$innerIP" ];then
        cp /etc/sysconfig/network-scripts/ifcfg-eth1 /etc/sysconfig/network-scripts/ifcfg-eth1.bak
        IP="10.`/sbin/ifconfig  eth0|awk -F[.\ ]+ '/inet addr:/{print $4"."$5"."$6}'`"
        sed -i '/ONBOOT/d' /etc/sysconfig/network-scripts/ifcfg-eth1
        sed -i '/HOTPLUG/d' /etc/sysconfig/network-scripts/ifcfg-eth1
        sed -i '/BOOTPROTO/d' /etc/sysconfig/network-scripts/ifcfg-eth1
        sed -i '/IPADDR/d' /etc/sysconfig/network-scripts/ifcfg-eth1
        sed -i '/NETMASK/d' /etc/sysconfig/network-scripts/ifcfg-eth1
        sed -i '/NETWORK/d' /etc/sysconfig/network-scripts/ifcfg-eth1
        sed -i '/BROADCAST/d' /etc/sysconfig/network-scripts/ifcfg-eth1
        echo "ONBOOT=yes" >> /etc/sysconfig/network-scripts/ifcfg-eth1
        echo "BOOTPROTO=static" >> /etc/sysconfig/network-scripts/ifcfg-eth1
        echo "IPADDR=$IP" >> /etc/sysconfig/network-scripts/ifcfg-eth1
        echo "NETMASK=255.0.0.0" >> /etc/sysconfig/network-scripts/ifcfg-eth1
        /etc/init.d/network restart >>${Log_file} 2>&1
        CheckIP="`/sbin/ifconfig  eth1|awk -F[:\ ]+ '/inet addr:/{print $4}'`"
        if [ "$IP" != "$CheckIP" ];then
                Showmsg "errorusermsg" "alter 7road server lan_ip error,pls check"
        fi
        fi
        ###检查config
        Check_file_exist "/root/config"
        db_ip=`awk  -F ":" '/db_ip/{print $2}' /root/config|sed 's/ //g'`
        web_ip=`awk -F":" '/web_ip/{print $2}' /root/config |sed 's/ //g'`
        agent=`awk -F":|_" '/site/{print $2}' /root/config |sed 's/ //g'`
        #ping -c 2 ${db_ip} >>${Log_file} 2>&1
        #Showmsg "errorsys" "ping db_ip ${db_ip} fail"
        #Ip_info=`getLocaltOuterIP`
        #echo "$Ip_info"|grep -q $web_ip
        #if [ $? = 0 ];then
        #        local Line_number=`cat /root/config |wc -l`
        #        [ $Line_number -lt 7 ] && Showmsg "errorusermsg" "config line number is less 7"
        #        dos2unix -q ${Config}
        #else
        #        Showmsg "errorusermsg" "config web_ip error,pls check"
        #fi
        ps -ef|grep -v grep|grep -q centerserver >>${Log_file} 2>&1 
        if [ $? = 0 ];then
                Showmsg "errorusermsg" "game is running,pls check"
        fi
        Showmsg "ok"
    
}
###add  end 2015-12-15 by jinliang

Install_init(){
        Showmsg "msg2" "install init server ......"
        ####kill user login for tty
        local Tty=`w|grep tty|awk '{print $2}'`
        for Ttyuser in  $Tty
        do
                pkill -kill -t ${Ttyuser}
        done

        #### yunm install
        killall yum-updatesd >>${Log_file} 2>&1
        killall yum-updatesd-helper >>${Log_file} 2>&1
        yum remove -y java >>${Log_file} 2>&1
        local Yum_software="nc wget curl gcc openssl-devel pcre-devel zlib-devel net-snmp* lrzsz dos2unix expect ntp mysql vim-common vim-enhanced \
        vixie-cron unzip zip sysstat make rsync bind-utils bc openssh-clients"
        yum -y install ${Yum_software} >>${Log_file} 2>&1
        Showmsg "errorsys" "yum install software fail."

        ####off service and on service
        for Stop_server in `chkconfig --list|awk '{print $1}'`;do
                /sbin/chkconfig --level 3 $Stop_server off >>${Log_file} 2>&1
        done

        Start_services="anacron crond iptables network portmap sshd snmpd syslog cpuspeed irqbalance"
        for Start_service in $Start_services;do
                /sbin/chkconfig --level 3 ${Start_service} on >>${Log_file} 2>&1
        done

        ##### Modify password
        echo $Password | passwd --stdin root >>${Log_file} 2>&1

        ##### Modify abrt
        Check_file_exist "/etc/abrt/abrt-action-save-package-data.conf"
        sed -i "s/ProcessUnpackaged = no/ProcessUnpackaged = yes/g" /etc/abrt/abrt-action-save-package-data.conf

        #### Start iptables
        mkdir -p /data/dandantang/gametools/
        cd /data/dandantang/gametools/
        rm -f web_iptable.sh
        Wget_file 'web_iptable.sh'
        Check_file_exist web_iptable.sh
        sh /data/dandantang/gametools/web_iptable.sh >>${Log_file} 2>&1
        cd /etc/snmp/
        mv snmpd.conf snmpd.conf_bak >>${Log_file} 2>&1
        Wget_file 'snmpd.conf'
        Check_file_exist snmpd.conf
        /etc/init.d/snmpd restart >>${Log_file} 2>&1
        cd /root
        rm -f auto_check.sh
        Wget_file 'auto_check.sh'
        Check_file_exist auto_check.sh
        
        ### alias grep
        sed -i /"alias grep"/d ~/.bashrc
        sed -i '/cp/d' ~/.bashrc
        echo "alias grep='grep --color'" >>~/.bashrc

        ##### Modify Selinux disabled
        cp -rf /etc/selinux/config   /etc/selinux/config_bak
        sed -i '/^SELINUX/s/SELINUX=.*/SELINUX=disabled/' /etc/selinux/config
        setenforce 0 >>${Log_file} 2>&1
        
        # Change resolv.conf
        sed -i '/114.114.114.114/d' /etc/resolv.conf
        sed -i '1i\nameserver 114.114.114.114\ ' /etc/resolv.conf
        
        ##### Modify ssh port 16333
        cd /etc/ssh/
        cp -rf sshd_config sshd_config.bak
        sed -i 's/GSSAPIAuthentication yes/GSSAPIAuthentication no/g' sshd_config
        sed -i 's/#UseDNS yes/UseDNS no/g' sshd_config
        sed -i 's/#ClientAliveInterval 0/ClientAliveInterval 60/g' sshd_config
        sed -i 's/#ClientAliveCountMax 3/ClientAliveCountMax 10/g' sshd_config
        sed -i -r 's/#Port 22|Port 22/Port 16333/g' sshd_config
        /etc/init.d/sshd restart >>${Log_file} 2>&1
        ##### Modify kernel parameters sysctl.conf file
        mv /etc/sysctl.conf /etc/sysctl.conf.`date +"%F-%H-%M-%S"`
        cat > /etc/sysctl.conf << EOF
# This script is created by tomas
# version:1.1 
net.ipv4.ip_forward = 1
net.ipv4.conf.default.rp_filter = 1
net.ipv4.conf.default.accept_source_route = 0
kernel.sysrq = 0
kernel.core_uses_pid = 1
net.ipv4.tcp_syncookies = 1
kernel.msgmnb = 65536
kernel.msgmax = 65536
kernel.shmmax = 68719476736
kernel.shmall = 4294967296
net.ipv4.tcp_fin_timeout = 30
net.ipv4.tcp_window_scaling = 0
net.ipv4.tcp_sack = 0
net.ipv4.tcp_synack_retries = 3
net.ipv4.tcp_syn_retries = 3
net.ipv4.tcp_keepalive_time = 30
net.ipv4.tcp_tw_reuse = 1
net.ipv4.tcp_tw_recycle = 1
net.ipv4.tcp_rmem = 4096 87380 16777216
net.ipv4.tcp_wmem = 4096 65536 16777216
net.ipv4.ip_local_port_range = 10000    65000
net.ipv4.tcp_max_syn_backlog = 8192
net.ipv4.tcp_max_tw_buckets = 5000
net.ipv4.tcp_max_orphans = 3276800
net.core.somaxconn = 262144
net.core.rmem_max = 16777216
net.core.wmem_max = 16777216
EOF
        /sbin/sysctl -p >>${Log_file} 2>&1

        ###add ntp to crontab
        crontab -r >>${Log_file} 2>&1
        echo "*/5 * * * * /usr/sbin/ntpdate time.windows.com >>/root/web.time" >> /var/spool/cron/root
        echo "30 4 * * * /bin/sh /data/dandantang/gametools/backup_log.sh >/dev/null 2>&1" >> /var/spool/cron/root
        /etc/init.d/crond restart >>${Log_file} 2>&1
        Showmsg "ok"
}

###install software (jdk、tomcat、nginx)
Install_Software(){
        Showmsg "msg2" "install software ......"
        #rm /usr/local/tomcat_interface_* -rf
        #Rm_exist_file '/usr/local/jdk1.7.0_80' '/usr/local/nginx-1.8.0' '/ccu' 
        ps aux|grep -P "nginx|TerminalCCU|tomcat"|awk '!/grep/{print $2}'|xargs kill -9 >/dev/null 2>&1
        cd ${The_file_dir}
        Wget_file 'nginx-1.8.0.tar.gz'
        Wget_file 'redis-3.2.3.tar.gz'
        #'resource/tomcat.zip' 'resource/jdk-6u24-linux-x64.bin.zip'
        Check_file_exist "${The_file_dir}/nginx-1.8.0.tar.gz"
        Check_file_exist "${The_file_dir}/redis-3.2.3.tar.gz"
        #'${The_file_dir}/tomcat.zip' '${The_file_dir}/jdk-6u24-linux-x64.bin.zip'
        #### install nginx tomcat jdk
        tar zxf nginx-1.8.0.tar.gz
        cd nginx-1.8.0
        ./configure --prefix=/usr/local/nginx-1.8.0 --user=nginx --group=nginx --with-http_ssl_module --with-http_flv_module --with-http_gzip_static_module >>${Log_file} 2>&1
        #groupadd -f nginx && useradd -g nginx nginx
        Showmsg "errorsys" "nginx ./configure fail"
        make >>${Log_file} 2>&1
        Showmsg "errorsys" "nginx make fail"
        make install >>${Log_file} 2>&1
        Showmsg "errorsys" "nginx make install fail"
        cd ${The_file_dir}
        tar zxf redis-3.2.3.tar.gz
        cd redis-3.2.3
        cp -r redis.conf /etc/
        make >>${Log_file} 2>&1
        Showmsg "errorsys" "redis make fail"
        make install >>${Log_file} 2>&1
        Showmsg "errorsys" "redis make install fail"
        echo "/usr/local/bin/redis-server /etc/redis.conf" >>/etc/rc.local
        cd /usr/local/bin
        mkdir -p /data/redis-log/
        ./redis-server /etc/redis.conf >>${Log_file} 2>&1
        Showmsg "errorsys" "redis startup fail"
        #####################################################################

        sed -i '/ccu/d' /etc/rc.local
        sed -i '/NGINX_HOME/d' /etc/profile
        sed -i '/JAVA_HOME/d' /etc/profile
        sed -i '/^PS1/d' /etc/profile
        sed -i '/102400/d' /etc/profile
        echo 'ulimit -n 102400' >> /etc/profile
        echo 'export NGINX_HOME=/usr/local/nginx-1.8.0' >> /etc/profile
        echo 'export PATH=$PATH:$NGINX_HOME/sbin' >> /etc/profile
        echo 'export JAVA_HOME=/usr/local/jdk1.7.0_80' >> /etc/profile
        echo 'export PATH=$PATH:$JAVA_HOME/bin:$JAVA_HOME/jre/bin' >> /etc/profile
        echo "PS1='\u@\[\e[0;31m\]<\H|\w>\[\e[m\]:\\$ '" >>/etc/profile
        Showmsg "ok"
}

###download_server function
Download_server() {
        Showmsg "msg2" "install download server ......"
        #echo "01 */04 * * * /bin/sh /game/server/sq_tool/check_nginx_log_error.sh >/dev/null 2>&1" >> /var/spool/cron/root
        #echo '*/10 * * * * cd /root;/bin/sh auto_check.sh >/dev/null 2>&1' >>/var/spool/cron/root
        #echo "00 05 * * * /bin/sh /game/server/app/createOrder.sh >/dev/null 2>&1" >> /var/spool/cron/root
        #restart crontab
        #crontab -r >/dev/null 2>&1
        #echo "*/5 * * * * /usr/sbin/ntpdate time.windows.com >>/root/web.time" >> /var/spool/cron/root

        #########################################################
        #rsync nginx jdk tomcat
        #Rsync_file "--exclude logs/" "root" "${Template_ip}" "/usr/local/nginx-1.8.0/" "/usr/local/nginx-1.8.0/" "${Password}" >>${Log_file}
        #Rsync_file "" "root" "${Template_ip}" "/root/.integration/" "//root/.integration//" "${Password}">>${Log_file}
        Rsync_file "" "root" "${Template_ip}" "/usr/lib64/libsigar-amd64-linux.so" "/usr/lib64/libsigar-amd64-linux.so" "${Password}">>${Log_file}
        Rsync_file "" "root" "${Template_ip}" "/usr/local/jdk1.7.0_80/" "/usr/local/jdk1.7.0_80/" "${Password}">>${Log_file}
        Rsync_file "--exclude logs/*" "root" "${Template_ip}" "/usr/local/tomcat/" "/usr/local/tomcat/" "${Password}">>${Log_file}
        Rsync_file "--exclude logs/" "root" "${Template_ip}" "/usr/local/etl/" "/usr/local/etl/" "${Password}" >>${Log_file}
        Rsync_file "--exclude logs/" "root" "${Template_ip}" "/usr/local/nginx-1.8.0/" "/usr/local/nginx-1.8.0/" "${Password}" >>${Log_file}
        Rsync_file "--exclude server/logs/* --exclude server/Results/* --exclude server/Querys/*" "root" "${Template_ip}" "/ccu/" "/ccu/" "${Password}" >>${Log_file}
        Rsync_file "--exclude Web/logs/ --exclude Web/request-log/ --exclude centerserver/log/ --exclude fightingserver/log/  --exclude gameserver1/log/  --exclude gameserver2/log/" \
        "root" "${Template_ip}" "/data/dandantang/" "/data/dandantang/" "${Password}" >>${Log_file}
        Check_file_exist "/usr/local/tomcat/conf/server.xml" "/usr/local/jdk1.7.0_80/bin/jps"
        #########################################################

        ###rsync server
        #[ -d /game ] && { rm -rf /game_bak;mv -f /game /game_bak; }
        mkdir -p /data/server_new
        mkdir -p /data/server_bak
        mkdir -p /data/dandantang
        mkdir -p /data/logs
        #rsync gameserver

        for Service_name in centerserver fightingserver gameserver1 gameserver2;do
            mkdir -p /data/dandantang/${Service_name}/log
        done
        mkdir -p /data/dandantang/Web/logs
        chmod 755 -R /usr/lib64/libsigar-amd64-linux.so
        #mkdir -p /data/dandantang/app/gateway{1..4}/log ; mkdir -p /data/dandantang/webapp/web/log
        #Check_file_exist '/data/dandantang/sq_tool/backup_log.sh'
        Showmsg "ok"
}


###Replace function
Replace() {
        if [ $# -eq 2  ];then
        Showmsg "msg2" "replace config......"
        #local Tmp=`mktemp`
        for Filename in `grep -r -w $1 /data/dandantang /usr/local/nginx-1.8.0/conf/ /root/config|awk -F":" '{print $1}' |sort|uniq|egrep -v "*.sql$|*.xls$"`;do 
            sed -i "s/\<$1\>/$2/g" $Filename
        #echo "$Filename" >> $Tmp
        #sed -i "s/$1/$2/g" $Filename
        done
        Showmsg "ok"
        else
            Showmsg "errorusermsg" "Usage: $0 replace  old(content) new(content)"
        fi
}

###add 2015-12-10 by jinliang
#取得本机内网IP
function getLocalInnerIP()
{
        ifconfig | grep 'inet addr:' | awk -F"inet addr:" '{print $2}'  | awk '{print $1}' | while read theIP; do
            A=$(echo $theIP | cut -d '.' -f1)
            B=$(echo $theIP | cut -d '.' -f2)
            C=$(echo $theIP | cut -d '.' -f3)
            D=$(echo $theIP | cut -d '.' -f4)
            int_ip=$(($A<<24|$B<<16|$C<<8|$D))
            #10.0.0.0(167772160)~10.255.255.255(184549375)
            if [ "${int_ip}" -ge 167772160 -a "${int_ip}" -le 184549375 ]; then
                echo $theIP
            elif [ "${int_ip}" -ge 2886729728 -a "${int_ip}" -le 2887778303 ]; then     #172.16.0.0(2886729728)~172.31.255.255(2887778303)
                echo $theIP
            elif [ "${int_ip}" -ge 3232235520 -a "${int_ip}" -le 3232301055 ]; then   #192.168.0.0(3232235520)~192.168.255.255(3232301055)
                echo $theIP
            fi
        done
}

#取得本机外网ip(如有多个，则以tab键分隔)
function getLocaltOuterIP()
{
        innerIP=`getLocalInnerIP`
        ifconfig | grep -E "(2[0-4][0-9]|25[0-5]|1[0-9][0-9]|[1-9]?[0-9])(\.(2[0-4][0-9]|25[0-5]|1[0-9][0-9]|[1-9]?[0-9])){3}" | awk -F":" '{print $2}' |\
            awk '{print $1}' | grep -vE "127.0.0.1|${innerIP}" | sed -e ':a;N;$ s/\n/\t/g;ba'
}

Select_template(){
    Showmsg "msg2" "install select server ......"
    cd ${The_file_dir}
    wget 'http://yw.7road-inc.com:8081/LoadGameData?g=1,2,5,31&s=1&gameId=35' -O sq_ip_list.txt.temp > /dev/null 2>&1
    Showmsg "errorsys" "wget sq_ip_list.txt.temp fail"
    cat sq_ip_list.txt.temp | dos2unix | grep '|||' | sed 's/^ *//g' | sed 's/ /*/g' | perl -p -i -e "s/(/--(/g" | sed 's/|||/\n/g' | tr '|' ' ' | awk '{if ($1~/--(/) ; print $0} ' \
        | perl -p -i -e "s/@@.*--//g" | awk '{if ($1~/--\(/) gsub("--(", "*");sub("合区)", "");print $0} ' | sed 's/@@//g' |awk '!/DB/{print $6"\t"$4"\t"$3}' |grep ${agent} |sort -r > template.txt
    Check_file_exist 'template.txt'
    agent=`awk -F":|_" '/site/{print $2}' /root/config |sed 's/ //g'`
    egrep -q "^${agent}_([0-9]{4})" ${The_file_dir}/template.txt
    Showmsg "errorsys" "${agent} not in template.txt."
    cat template.txt |while read Temp_site Temp_ip  Temp_wip
    do
            #Auto_ssh ${Temp_ip} "hostname" "10" "1" 错误继续执行，取到template_ip为止
            Auto_ssh ${Temp_ip} "ifconfig" "10" "1"
            grep -q  ${Temp_wip} ssh.log
            if [ "$?" = 0 ];then
        grep -w ${Temp_wip} template.txt > Template_ip.txt
        rm -f template.txt sq_ip_list.txt.temp
        break
    fi
    done
    Template_ip=`awk '{print $2}' Template_ip.txt`
    [ ! -z "${Template_ip}" ]
    Showmsg "errorsys" "template_ip is null"
    Showmsg "ok"
 }

###Select template ip and Timezone from tbj for installmanual
Select_template_tbj(){
        Showmsg "msg2" "install select server ......"
        cd ${The_file_dir}
        rm -rf ${The_file_dir}/Template_ip.txt
        Wget_file 'Template_ip.txt'
        dos2unix Template_ip.txt >>${Log_file} 2>&1
        Check_file_exist "/root/config" "${The_file_dir}/Template_ip.txt"
        agent=`awk -F":|_" '/site/{print $2}' /root/config |sed 's/ //g'`
        egrep -q "^${agent}_([0-9]{4})" ${The_file_dir}/Template_ip.txt
        if [ $? = 0 ];then
                Template_ip=`egrep "^${agent}_([0-9]{4})" ${The_file_dir}/Template_ip.txt |awk -F":::" '{print $2}'|sed 's/ //g'`
                [ ! -z "${Template_ip}" ]
                Showmsg "errorsys" "template_ip is null"                
                nc -nvz -w 5 ${Template_ip} 16333 >>${Log_file} 2>&1
                Showmsg "errorsys" "template_ip ${Template_ip} 16333 port is not open"
        else
                Showmsg "errorusermsg" "$agent is not exist in ${The_file_dir}/Template_ip.txt"
        fi
    Showmsg "ok"
}
    
Alter_config(){
    cd ${The_file_dir}
    Check_file_exist "/root/config" 
    Config="/root/config"
    ###Define variables for new game server
    Rsync_file "" "root" "${Template_ip}" "/root/config" "/root/Template_config" "${Password}" >>${Log_file} 2>&1
    Check_file_exist "/root/Template_config"
    areaid=`awk -F":" '/area_id/{print $2}' ${Config} |sed 's/ //g'`
    site=`awk -F":" '/site/{print $2}' ${Config} |sed 's/ //g'`
    areaname=`awk -F":" '/area_name/{print $2}' ${Config} |sed 's/ //g'`
    web_ip=`awk -F":" '/web_ip/{print $2}' ${Config} |sed 's/ //g'`
    db_ip=`awk -F":" '/db_ip/{print $2}' ${Config} |sed 's/ //g'`
    flash_url=`awk -F":" '/flash_url/{print $2}' ${Config} |sed 's/ //g'`
    res_url=`awk -F":" '/res_url/{print $2}' ${Config} |sed 's/ //g'`
    quest_url=`awk -F":" '/quest_url/{print $2}' ${Config} |sed 's/ //g'`
    title=`awk -F":" '/title/{print $2}' ${Config} |sed 's/ //g'`
    login_key=`awk -F":" '/login_key/{print $2}' ${Config} |sed 's/ //g'`
    charge_key=`awk -F":" '/charge_key/{print $2}' ${Config} |sed 's/ //g'`
    sed -i '/db_tank/d;/db_logs/d' ${Config}
    java -jar /data/dandantang/gametools/Decurpt.jar $db_ip &>/tmp/dblog
    sed -i '/ERROR/d' /tmp/dblog
    db_tankid=`sed -n '1,3p' /tmp/dblog |tr -d '\n'`
    db_logsid=`sed -n '4,6p' /tmp/dblog |tr -d '\n'`
    echo "db_tank:${db_tankid}" >> ${Config}
    echo "db_logs:${db_logsid}" >> ${Config}
    db_tank=`awk -F":" '/db_tank/{print $2}' ${Config} |sed 's/ //g'`
    db_logs=`awk -F":" '/db_logs/{print $2}' ${Config} |sed 's/ //g'`
    rm -f /tmp/dblog
    Template_config="/root/Template_config"
    Template_areaid=`awk -F":" '/area_id/{print $2}' ${Template_config} |sed 's/ //g'`
    Template_site=`awk -F":" '/site/{print $2}' ${Template_config} |sed 's/ //g'`
    Template_areaname=`awk -F":" '/area_name/{print $2}' ${Template_config} |sed 's/ //g'`
    Template_web_ip=`awk -F":" '/web_ip/{print $2}' ${Template_config} |sed 's/ //g'`
    Template_db_ip=`awk -F":" '/db_ip/{print $2}' ${Template_config} |sed 's/ //g'`
    Template_flash_url=`awk -F":" '/flash_url/{print $2}' ${Template_config} |sed 's/ //g'`
    Template_res_url=`awk -F":" '/res_url/{print $2}' ${Template_config} |sed 's/ //g'`
    Template_quest_url=`awk -F":" '/quest_url/{print $2}' ${Template_config} |sed 's/ //g'`
    Template_title=`awk -F":" '/title/{print $2}' ${Template_config} |sed 's/ //g'`
    Template_login_key=`awk -F":" '/login_key/{print $2}' ${Template_config} |sed 's/ //g'`
    Template_charge_key=`awk -F":" '/charge_key/{print $2}' ${Template_config} |sed 's/ //g'`
    Template_db_tank=`awk -F":" '/db_tank/{print $2}' ${Template_config} |sed 's/ //g'`
    Template_db_logs=`awk -F":" '/db_logs/{print $2}' ${Template_config} |sed 's/ //g'`
    # grep -w $Template_config_ip Template_ip.txt  >>${Log_file} 2>&1
    # Showmsg "errorsys" "pls check Template_config web ip is error"
    Showmsg "msg2" "install alter config ......"
    ###Replace confing
    Replace "${Template_areaid}" "${areaid}" >>${Log_file} 2>&1
    Replace "${Template_areaname}" "${areaname}" >>${Log_file} 2>&1
    Replace "${Template_web_ip}" "${web_ip}" >>${Log_file} 2>&1
    Replace "${Template_flash_url}" "${flash_url}" >>${Log_file} 2>&1
    Replace "${Template_quest_url}" "${quest_url}" >>${Log_file} 2>&1
    Replace "${Template_res_url}" "${res_url}" >>${Log_file} 2>&1
    Replace "${Template_title}" "${title}" >>${Log_file} 2>&1
    Replace "${Template_login_key}" "${login_key}" >>${Log_file} 2>&1
    Replace "${Template_charge_key}" "${charge_key}" >>${Log_file} 2>&1
    Replace "${Template_db_tank}" "${db_tank}" >>${Log_file} 2>&1
    Replace "${Template_db_logs}" "${db_logs}" >>${Log_file} 2>&1
    ###change hostname
    sed -i '/HOSTNAME/d' /etc/sysconfig/network
    echo "HOSTNAME=web_$site" >>/etc/sysconfig/network
    hostname web_$site
    hostname >>${Log_file} 2>&1
    sed -i "/127.0.0.1/d;/web_$site/d;/${web_ip}/d" /etc/hosts
    echo "127.0.0.1 localhost.localdomain localhost web_$site" >> /etc/hosts
    echo "${web_ip} web_${site}" >> /etc/hosts
    Alarm_Control "OFF"
    Showmsg "ok"
}

###add end 2015-12-10 by jinliang

###file ready
File_ready(){
        Showmsg "msg2" "file ready ......"
        #delete old server
        rm -rf ${Server_new}/dandantang
        ##backup game/server
        /usr/bin/rsync  -avz --delete --exclude "AreaRankServer1/log/*" --exclude "centerserver/log/*" --exclude "gameserver1/log/*" \
            --exclude "gameserver2/log/*" --exclude "fightingserver/log/*" --exclude "Web/logs/*" --exclude "Web/request-log/*" ${Game_server} ${Server_bak} >/dev/null 2>&1
        Showmsg "errorsys" "file backup faild"
        unzip -q ${Server_new}/dandantang.zip -d ${Server_new}/.
        Showmsg "errorsys" "unzip dandantang.zip fail"
        [ -f ${Server_new}/dandantang/file.md5 ]
        Showmsg "errorsys" "file.md5 not found,fail"
        Showmsg "ok"
}

###file update
File_update(){
        Showmsg "msg2" "file update ......"
        rm -rf ${Server_new}/update.log
        [ -d ${Server_new}/dandantang ]
        Showmsg "errorsys" "The dir ${Server_new}/dandantang does not exist,fail"

        [ -d ${Game_server} ]
        Showmsg "errorsys" "The dir ${Game_server} does not exist,fail"

        [ -f "${Server_new}/dandantang/file.md5" ]
        Showmsg "errorsys" "${Server_new}/dandantang/file.md5 does not exist,fail"

        dos2unix -q ${Server_new}/dandantang/file.md5
        cp -rf ${Server_new}/dandantang/* ${Game_server}/.
        cd ${Game_server}
        md5sum -c file.md5 >>${Server_new}/update.log 2>&1
        local Count_ok=`grep -c OK ${Server_new}/update.log`
        local Count_file=`cat "${Server_new}/dandantang/file.md5" |grep -v "#" |grep "*" |wc -l`
        local Count_failed=`grep -c FAILED ${Server_new}/update.log`
        [ "${Count_ok}" = "${Count_file}" ] && [ "${Count_failed}" = "0" ]
        Showmsg "errorsys" "update fail"
        Showmsg "ok"
}

Start_game(){
    Showmsg "msg1" "start game ......"
    local Sleep_time="$1"
    local center_path="/data/dandantang/center.sh"
    local fight_path="/data/dandantang/fight.sh"
    local game_path="/data/dandantang/game.sh"
    local Web_path="/data/dandantang/web.sh"
    # 启动redis进程
    ps -ef |grep -v grep |grep redis >/dev/null 2>&1
    if [ $? = 1 ];then
    redis-server /etc/redis.conf >/root/start.log 2>&1
    sleep 3
    else
    Showmsg "msg1" "start redus success......"
    fi
    #判断是否要启动RankServer
    hostname|grep -qi web
    if [ $? -eq 0 ];then
    local host=`hostname`
    local lastnum=`echo $host|awk -F_ '{print $NF}'`
    if [ "$lastnum" == "0001" ];then
        local Rank_path="/data/dandantang/arearank.sh"      
        sh "$Rank_path" start >/root/start.log 2>&1
        Showmsg "errorsys" "rank start fail"
        sleep 5
        local rank_num=`jps|grep -cP "RankServer"`
        [ "$rank_num" -eq 1 ]
        Showmsg "errorsys" "rank start fail"
    fi
    fi
     # 启动center
    Showmsg "msg2" "start center ......"
    sh "$center_path" start >/root/start.log 2>&1
    Showmsg "errorsys" "center start fail"
    sleep 20
    local center_num=`jps|grep -cP "CenterServer"`
    [ "$center_num" -eq 1 ]
    Showmsg "errorsys" "center start fail"
    Showmsg "ok"
    # 启动fight
    Showmsg "msg2" "start fight ......"
    #sh "$fight_path" start $Site $Key> /root/start.log 2>&1
    sh "$fight_path" start > /root/start.log 2>&1
    Showmsg "errorsys" "fight start fail"
    sleep 15
    local fight_num=`jps|grep  -c "FightingServer"`
    [ "$fight_num" -eq 1 ]
    Showmsg "errorsys" "fight start fail"
    Showmsg "ok"
    # 启动game
    Showmsg "msg2" "start game ......"
    #sh "$game_path" start $Site $Key> /root/start.log 2>&1
    sh "$game_path" start > /root/start.log 2>&1
    Showmsg "errorsys" "game start fail"
    sleep 30
    local game_num=`jps|grep  -c "GameServer"`
    [ "$game_num" -eq 2 ]
    Showmsg "errorsys" "game start fail"
    Showmsg "ok"
    # 启动web.sh
    Showmsg "msg2" "start web ......"
    sh "$Web_path" start > /root/start.log 2>&1
    Showmsg "errorsys" "web start fail"
    sleep 3
    local Web_num=`ps -ef|grep -c "tomcat"`
    [ "$Web_num" -eq 2 ]
    Showmsg "errorsys" "web start fail"
    #nginx 进程不在启动nginx，存在reload
    ps -ef |grep -v grep |grep nginx >/dev/null 2>&1
    if [ $? = 1 ];then
    /usr/local/nginx-1.8.0/sbin/nginx >>/root/start.log 2>&1
    ps -ef |grep -v grep |grep nginx >/dev/null 2>&1
    Showmsg "errorsys" "nginx start fail"
    else
    /usr/local/nginx-1.8.0/sbin/nginx -s reload  >/dev/null 2>&1
    fi
    Alarm_Control "ON"
    Showmsg "ok"
    CreateAllXml
}

Change_Ver(){
    Check_file_exist /data/server_new/dandantang/Web/update.xml
    java -jar /data/dandantang/gametools/UpdatePatchJar.jar /data/dandantang/Web/update.xml /data/dandantang/Web/Flash/config.xml >>/root/start.log 2>&1
    java -jar /data/dandantang/gametools/UpdatePatchJar.jar /data/dandantang/Web/update.xml /data/dandantang/Web/Resource/flash/config.xml >>/root/start.log 2>&1
}

CreateAllXml(){
    sh /data/dandantang/createxml.sh >>/root/start.log 2>&1
}

### 停游戏,可以是stop, stopnow
Stop_game(){
    # Stop_var可以时"stop", "stopnow"
    # Sleep_time是"stop"或是"stopnow"时对应睡眠时间
    local Stop_var="$1"
    local Sleep_time="$2"
    #Site=`awk -F":" '/site/{print $2}' /root/config |sed 's/ //g'`
    local center_path="/data/dandantang/center.sh"
    local fight_path="/data/dandantang/fight.sh"
    local game_path="/data/dandantang/game.sh"
    local Web_path="/data/dandantang/web.sh"
    # 停center,game
    Showmsg "msg2" "stoping center,fight,game ......"
    Alarm_Control "OFF"
    # Showmsg "msg2" "stoping gate.sh ......"
    sh "$center_path" "$Stop_var" >> /root/stop.log 2>&1
    sleep $Sleep_time
    sh "$fight_path" "stop" >> /root/stop.log 2>&1
    sh "$game_path" "stop" >> /root/stop.log 2>&1
    # 如果当前还有game进程, 强杀
    local game_num=`jps|grep -cP "CenterServer|FightingServer|GameServer"`
    if [ "$game_num" != "0" ]; then
        # 这样写简单, 但是会执行两遍jps命令
        jps|grep -P "CenterServer|FightingServer|GameServer"|awk '{print $1 }'|xargs kill -9 >>$Log_file 2>&1
        sleep 3 
    fi
    Showmsg "ok"
    # 开始停web.sh
    Showmsg "msg2" "stoping web ......"
    sh "$Web_path" stop >>$Log_file 2>&1
    sleep 5
    # 如果当前还有对应tomcat进程, 强杀
    local Web_num=`ps -ef|grep -c "tomcat"`
    if [ "$Web_num" != "0" ]; then
        ps -ef|awk '/tomcat/&&!/awk/{print $2}'|xargs kill -9 >>$Log_file 2>&1
        sleep 1
    fi
    Showmsg "ok"
    # 判断是否要停RankServer
    hostname|grep -qi web
    if [ $? -eq 0 ];then
    local host=`hostname`
    local lastnum=`echo $host|awk -F_ '{print $NF}'`
    # 停RankServer
    if [ $lastnum == "0001" ];then
        local Rank_path="/data/dandantang/arearank.sh"      
        sh "$Rank_path" stop >/root/stop.log 2>&1
        Showmsg "errorsys" "rank stop fail"
        sleep 5
        local rank_num=`jps|grep -cP "RankServer"`
        [ "$rank_num" -eq 0 ]
        Showmsg "errorsys" "rank stop fail"
    fi
    fi
    Showmsg "msg2" "stoping redis ......"
    ps aux|grep -P "redis"|awk '!/grep/{print $2}'|xargs kill -9 >>$Log_file 2>&1
    Showmsg "ok"
}

### stop ccu
Ccu_stop(){
    Showmsg "msg2" "stop ccu ......"
    sh /ccu/server/shutdown.sh >>$Log_file 2>&1
    sleep 5
    netstat -ntupl|grep -q 8004 && lsof -i:8004|awk '!/PID/{print $2}'|xargs kill -9 &> /dev/null
    Showmsg "ok"
}

### start ccu
Ccu_start(){
    Showmsg "msg2" "start ccu ......"
    cd /ccu/server/
    sh startup.sh >>$Log_file 2>&1
    sleep 5
    netstat -ntupl|grep -q 8004
    Showmsg "errorsys" "start ccu fail"
    Showmsg "ok"
}

### restart etl
Etl_restart(){
    sh /usr/local/etl/restartETL.sh >>$Log_file 2>&1
    sleep 5
    /usr/local/jdk1.7.0_80/bin/jps|grep -q MRClient
    Showmsg "errorsys" "restart etl fail"
}

Stop_crossserver(){
    Showmsg "msg2" "stop CrossServer ......"
    sh /data/dandantang/arearank.sh stop >>${Log_file} 2>&1
    sh /data/dandantang/fight.sh stop >>${Log_file} 2>&1
    sleep 10
    jps|grep FightingServer|RankServer |awk '{print $1}'|xargs kill >>${Log_file} 2>&1
    sleep 10
    local Cross_isrun=`jps |grep -cP "FightingServer|RankServer"`
    [ "${Cross_isrun}" = "0" ]
    Showmsg "errorsys" "stop CrossServer fail"
    Showmsg "ok"
}

### start crossserver
Start_crossserver(){
        Showmsg "msg2" "Start CrossServer ......"
        sleep 10
        sh /data/dandantang/arearank.sh start >>${Log_file} 2>&1
        sh /data/dandantang/fight.sh start >>${Log_file} 2>&1
        sleep 20
        Cross_num=`jps|grep -c -cP "FightingServer|RankServer"`
        [ "${Cross_num}" -gt "4" ]
        Showmsg "errorsys" "Start CrossServer fail"
        Showmsg "ok"
}

### 游戏自动更新(停服、备份、解压、覆盖、加版本修改配置、刷表、启动、重启tomcat、刷模板)
Auto_update(){
    if [ "${Updatetype}" = "web" ]; then
        Stop_game stop 330
        File_ready
        File_update
        Start_game 
    elif [ "${Updatetype}" = "kuafu" ]; then
        Stop_crossserver
        File_ready
        File_update
        Start_crossserver 
    else
        Showmsg "errorusermsg" "update type is not web or kuafu"
    fi
}

### 游戏自动更新(停服、备份、解压、覆盖、加版本修改配置)
Auto_stop(){
    if [ "${Updatetype}" = "web" ]; then
        Stop_game stop 330
        File_ready
        File_update
    elif [ "${Updatetype}" = "kuafu" ]; then
        Stop_crossserver
        File_ready
        File_update
    else
        Showmsg "errorusermsg" "update type is not web or kuafu"
    fi
}

### 游戏自动更新(刷表、启动、重启tomcat、刷模板)
Auto_start(){
    if [ "${Updatetype}" = "web" ]; then
        Start_game 
    elif [ "${Updatetype}" = "kuafu" ]; then
        Start_crossserver 
    else
        Showmsg "errorusermsg" "update type is not web or kuafu"
    fi
}

Auto_cover(){
    if [ "${Updatetype}" = "web" ]; then
        File_ready
        File_update
        CreateAllXml
    elif [ "${Updatetype}" = "kuafu" ]; then
        File_ready
        File_update
    else
        Showmsg "errorusermsg" "update type is not web or kuafu"
    fi
}

Usage(){
        cat <<EOF

Usage: $0 接的参数说明如下：
        [-h|--help|help]    #显示帮助
        [start]             #起服
        [stop]              #倒计时5分钟停服
        [stopnow]           #不倒计时停服
        [restart]           #重启游戏(stponow start)
        [check]             #服务器与游戏状态检查
        [replace 1 2]       #配置文件内容替换(域名或者ip的替换等)1是源 2是目标
        [file_ready]        #更新前准备(备份目录和解压更新文件)
        [file_update]       #更新游戏目录以及MD5验证
        [changever]         #更新版本号
        [createxml]         #刷模板
        [stopccu]           #ccu停止
        [startccu]          #ccu启动
        [reccu]             #ccu重启
        [autostop]          #游戏自动更新(停服、备份、解压、覆盖)
        [autostart]         #游戏自动更新(启动、加版本刷模板)
        [autocover]         #游戏在线更新(备份、解压、覆盖、重启tomcat、刷模板)
        [installweb]        #搭建gs(web从运维中控取模板)
        [installmanual]     #搭建gs(web从跳板机取模板，从运维中控取不到模板情况使用，使用生成的config)    
        [alterconfig]       #修改游戏配置文件 (跳板机需要配置相应config模板)

#最后一个参数是y表示不更新和提示游戏在线确认(跳过交互)
Usage1: $0 check
Usage2: $0 start

EOF
}

### 搭建gs(web从运维中控取模板)
Install_web(){
        date_1=`date +%s`
        Check_network
        Install_init
        Install_Software
        Select_template
        Download_server
        Alter_config
        Etl_restart
        echo  -e "\033[41;42m install OK \033[0m" >>${Log_file} 2>&1
        date_2=`date +%s`
        date_sum=$((${date_2}-${date_1}))
        echo "Elapsed time: ${date_sum} s"
}

### 搭建gs(web从跳板机取模板)
Install_manual(){
        date_1=`date +%s`
        Check_network
        Install_init
        Install_Software
        Select_template_tbj
        Download_server
        Alter_config
        Etl_restart
        echo  -e "\033[41;42m install OK \033[0m" >>${Log_file} 2>&1
        date_2=`date +%s`
        date_sum=$((${date_2}-${date_1}))
        echo "Elapsed time: ${date_sum} s"
}

Load_check(){
    # Cpu_idle=`vmstat|awk 'NR==3{printf $(NF-2)}'`
    local Cpu_idle=`iostat -c|awk 'NF{print $NF}'|tail -n1`
    local Cpu_load=`awk 'BEGIN{printf "%.1f", 100-"'$Cpu_idle'"}'`
    # Sys_load=`uptime|awk '{print $(NF-2), $(NF-1), $NF}'|tr ',' ' '`
    local Sys_load=`uptime|awk -F"[:]" '{gsub(/^ |,/,"",$NF);print $NF}'`
    local Sys_load_cur=`echo "$Sys_load"|awk '{print $1}'`
    # Mem_info=`free -m|awk '/Mem:/{printf $2 " "}/cache:/{printf $3}'`
    local Mem_total=`free -m|awk '/Mem:/{print $2}'`
    local Mem_free=`free -m|awk '/Mem:/{print $4}'`
    local Mem_free_per=`awk 'BEGIN{printf "%.1f", "'$Mem_free'"/"'$Mem_total'"*100}'`
    local Disk_root=`df -h|awk '$NF=="/"{print $(NF-1)+0}'`
    local Disk_usr_local=`df -h|awk '$NF~"/usr/local"{print $(NF-1)+0}'`

    # define color
    local tt=`awk 'BEGIN{if("'$Cpu_load'"-70<0){print 0}else{print 1}}'`
    if [ "$tt" -eq 0 ]; then
        local Cpu_load_color='green'
    else
        local Cpu_load_color='red'
    fi

    local tt=`awk 'BEGIN{if("'$Sys_load_cur'"-0.8<0){print 0}else{print 1}}'`
    if [ "$tt" -eq 0 ]; then
        local Sys_load_color='green'
    else
        local Sys_load_color='red'
    fi

    local tt=`awk 'BEGIN{if("'$Mem_free_per'"-10>0){print 0}else{print 1}}'`
    if [ "$tt" -eq 0 ]; then
        local Mem_free_per_color='green'
    else
        local Mem_free_per_color='red'
    fi

    local tt=`awk 'BEGIN{if("'$Disk_root'"-90<0){print 0}else{print 1}}'`
    if [ "$tt" -eq 0 ]; then
        local Disk_root_color='green'
    else
        local Disk_root_color='red'
    fi

    local tt=`awk 'BEGIN{if("'$Disk_usr_local'"-90<0){print 0}else{print 1}}'`
    if [ "$tt" -eq 0 ]; then
        local Disk_usr_local_color='green'
    else
        local Disk_usr_local_color='red'
    fi

    OLD_IFS="$IFS"
    IFS='______________________________________________________________'
    printf "%-10s%12s%25s | " "Load" "Cpu_load:" \
        `Color_str "$Cpu_load_color" "$Cpu_load%"`
    printf "%12s%25s\n" "Sys_load:" \
        `Color_str "$Sys_load_color" "$Sys_load"`
    printf "%-10s%12s%25s | " "Memory" "total:" `Color_str green "${Mem_total}MB"`
    printf "%12s%25s\n" "free:" \
        `Color_str "$Mem_free_per_color" "$Mem_free_per%"`
    printf "%-10s%12s%25s | " "Disk" "/:" \
        `Color_str "$Disk_root_color" "$Disk_root%"`
    printf "%12s%25s\n" "/usr/local:" \
        `Color_str "$Disk_usr_local_color" "$Disk_usr_local%"`
    IFS="$OLD_IFS"
}

### 分区是否只读检测
Disk_read_only_check(){
    local Tmp_file=`mktemp`
    local Path1='/'
    local Path2='/usr/local/'
    local Test_file='read-only.test'

    # '/' read-only check
    # 用能否在对应分区创建文件来检测
    touch "$Path1""$Test_file" &>$Tmp_file
    if `grep -q "Read-only file system" "$Tmp_file"`; then
        local Check_result="YES"
        local Check_result_color='red'
    else
        local Check_result="NO"
        local Check_result_color='green'
    fi
    printf "%-10s%12s%25s | " "Read-only" "/:" \
        `Color_str "$Check_result_color" "$Check_result"`

    # '/usr/local' read-only check
    touch "$Path2""$Test_file" &>$Tmp_file
    if `grep -q "Read-only file system" "$Tmp_file"`; then
        local Check_result="YES"
        local Check_result_color='red'
    else
        local Check_result="NO"
        local Check_result_color='green'
    fi
    printf "%12s%25s\n" "/usr/local:" \
        `Color_str "$Check_result_color" "$Check_result"`
    rm $Tmp_file -f &> /dev/null
}

DNS_Check(){
    DianXinIP="$Ip_eth0"
    LianTongIP="${Ip_eth0_1}"
    #DomainAll="`awk '/server_name/&&/\./&&!/^#/{print $2}' /usr/local/nginx-1.8.0/conf/vhosts/vhost.conf |tr -d ";"`"
    DomainAll="`grep server_name /usr/local/nginx-1.8.0/conf/vhosts/vhost.conf |awk '{for(i=2;i<=NF;i++)printf $i" ";printf "\n" }' |tr -d ";"`"
    for i in $DomainAll
    do
        nslookupIP="`nslookup $i|awk '/Address: [0-9\.]+$/{print $2}'|awk 'END{print}'|tr -d " "`"
        if [[ "$nslookupIP" == "$DianXinIP" || "$nslookupIP" == "$LianTongIP" ]];then
            Show_result "`printf '%-30s' $i` `printf '%20s' $nslookupIP`" 0
        else
            Show_result "`printf '%-30s' $i` `printf '%20s' $nslookupIP`" 1
        fi
    done
}


Network_check(){
    local Eth_interface=`ls /etc/sysconfig/network-scripts/ifcfg-eth[01]|\
        grep -oP "eth\d"`
    for Interface in $Eth_interface
    do
        local Interface_link=`ethtool $Interface|awk '/Link/{print $NF}'`
        local Interface_duplex=`ethtool $Interface|awk '/Duplex/{print $NF}'`
        local Interface_speed=`ethtool $Interface|awk '/Speed/{print $NF}'`

        # 不同操作系统, 网卡, 及网卡驱动对ethtool命令支持程度不懂, 可能会导致上面三个变量出现空值
        # 所以需要检查一下, 如果为空值, 则把三个变量值置为"unknow"
        [ -z "$Interface_link" ] && Interface_link='unknow'
        [ -z "$Interface_duplex" ] && Interface_duplex='unknow'
        [ -z "$Interface_speed" ] && Interface_speed='unknow'

        if [ "$Interface_link" = "yes" ]; then
            Link_color='green'
        else
            Link_color='red'
        fi
        # =~用于 [[ "正则匹配"
        if [[ "$Interface_speed" =~ "100" ]]; then
            Speed_color='green'
        else
            Speed_color='red'
        fi
        # 用[[ 配合 ||(&&)是一个短路运算
        if [[ "$Interface_duplex" = "Full" || "$Interface_duplex" = "full" ]]; then
            Duplex_color='green'
        else
            Duplex_color='red'
        fi

        # 输出对齐设置
        if [ "$Interface_duplex" = "unknow" ]; then
            printf "%-11s  link:%-23s  speed:%-25s  mode:%-18s\n" \
                "$Interface" \
                "`Color_str "$Link_color" $Interface_link`" \
                "`Color_str "$Speed_color" $Interface_speed`" \
                "`Color_str "$Duplex_color" $Interface_duplex`"
        else
            printf "%-11s  link:%-23s  speed:%-27s  mode:%-18s\n" \
                "$Interface" \
                "`Color_str "$Link_color" $Interface_link`" \
                "`Color_str "$Speed_color" $Interface_speed`" \
                "`Color_str "$Duplex_color" $Interface_duplex`"
        fi
    done
}

### display result like "service"
# Status_code为0 则结果为success
# Status_code为1 则结果为failed
# Status_code为2 则结果为unknow

Show_result(){
    if [ "$#" -ne 2   ]; then
        echo "Illegal function call"
        echo "$FUNCNAME Action statuscode"
        exit 1
    fi

    local Action="$1"
    local Status_code="$2"
    local Format="%-60s [ %s ]\n"

    if [ $Status_code -eq 0 ]; then
        local Result=' OK '
        local Color='green'
    elif [ $Status_code -eq 1 ];then
        local Result='FAIL'
        local Color='red'
    elif [ $Status_code -eq 2 ];then
        local Result='unknow'
        local Color='yellow'
    fi

    # save IFS
    OLD_IFS="$IFS"
    # define new IFS
    IFS='______________________________________________________________'
    printf "$Format" "$Action" "`Color_str $Color $Result`"
    # backup IFS
    IFS="$OLD_IFS"
}

### initalize vars
Check_init(){
    source /etc/profile
    # 只包含外网IP
    Ip_info=`getLocaltOuterIP`
    Ip_eth0=`echo "$Ip_info"|awk '{print $1}'`
    Ip_eth0_1=`echo "$Ip_info"|awk '{print $2}'`

    Db_con_port=2433
    Db_inner_ip=`awk  -F ":" '/db_ip/{print $2}' /root/config|sed 's/ //g'`

    Nginx_conf='/usr/local/nginx-1.8.0/conf/nginx.conf'
}

# 游戏进程检测, 包含jps显示的进程, tomcat, nginx 进程
Game_check(){
    local Process_key="(center|fighting|game)server"
    local Process_num=`ps -ef|\
    grep -P "(center|fighting|game)server"|\
    awk '!/cross|grep/{print $2}'|wc -l`

    if [ "$Process_num" -eq 4 ]; then
        local Status_code=0
    else
        local Status_code=1
    fi
    Show_result "Process_num:    $Process_num" "$Status_code"       

    # process check
    local tt="centerserver fightingserver gameserver1 gameserver2"
    for Process in $tt
    do
        # 设置默认状态为1(失败状态)
        local Status_code=1
        local Process_pid=`ps -ef|\
            awk '/'"$Process"'/&&!/awk|grep/{print $2}'`
        if [ ! -z "$Process_pid" ]; then
            local Status_code=0
        fi
        Show_result "$Process" "$Status_code"
    done
    # nginx check
    local Default_nginx_num=`awk '/worker_processes/{print $2+1}' "$Nginx_conf"`
    local Current_nginx_num=`pgrep nginx|wc -l`
    if [ "$Default_nginx_num" -eq "$Current_nginx_num" ]; then
        local Status_code=0
    else
        local Status_code=1
    fi
    Show_result "Nginx" "$Status_code"

    # tomcat check
    local Curren_tomcat_num=`ps -ef|grep tomcat|grep -v grep|wc -l`  
    if [ $Curren_tomcat_num -eq 1  ]; then
        local Status_code=0
    else
        local Status_code=1
    fi
    Show_result "Tomcat" "$Status_code"

    # redis check
    local Curren_redis_num=`ps -ef|grep redis|grep -v grep|wc -l`  
    if [ $Curren_redis_num -eq 1  ]; then
        local Status_code=0
    else
        local Status_code=1
    fi
    Show_result "Redis" "$Status_code"
}

### ccu进程监测
Ccu_check(){
    local Ccu_pid=`ps -ef|awk '/TerminalCCU/&&!/awk/{print $2}'`
    if [ ! -z "$Ccu_pid" ]; then
        Show_result "CCU" 0
    else
        Show_result "CCU" 1
    fi
}
Db_port_check(){
    if `nc -nvz -w 3 "$Db_inner_ip" "$Db_con_port" &>/dev/null`; then
        local Status_code=0
    else
        local Status_code=1
    fi
    Show_result "Db_port" "$Status_code"
  }

### 防火墙检测
Iptable_check(){
    iptables -L -n|grep -q "Chain FORWARD (policy DROP)"
    local Ret_num="$?"
    Show_result "Iptable" "$Ret_num"
}

### 游戏在线人数监测
Online_check(){
    local Ports="9200 9300"
    local Sum_online=0
    for Port in $Ports
    do
        local Port_online=`netstat -ntp|awk -v a=0 \
            '/ESTABLISHED/&&$4~":'$Port'"{a+=1}END{print a}'`
        Sum_online=$(($Sum_online + $Port_online))
        case $Port in
            9200)
                Port_name="Gameserver1_port(9200):"
                ;;
            9300)
                Port_name="Gameserver2_port(9300):"
                ;;
        esac
        printf "%-25s%14s\n" "$Port_name" `Color_str green "$Port_online"`
    done

    printf "%-25s%14s\n" "Gameonline:" `Color_str green "$Sum_online"`
}

Check(){
#echo $Site
echo '-------------------------- Hardware Info. ----------------------------'
Load_check 
Disk_read_only_check 
echo '-------------------------- Network  Info.  ---------------------------'
Network_check 
echo '-------------------------- Game Status Info.  ------------------------'
Game_check
Ccu_check
Db_port_check
DNS_Check
Iptable_check
echo Site:$Site
echo '-------------------------- Online Info.  ----------------------------'
Online_check
}

main(){
source /etc/profile
Site=`awk -F":" '/site/{print $2}' /root/config |sed 's/ //g'`
Password='#!9BVAPlDJ2%Nj@z'
Http_password='love7road.com'
Http_user='7roadddt'
The_file_dir=`echo $(cd "$(dirname "$0")"; pwd)`
Log_file='/opt/game.log' # 脚本日志文件
>$Log_file
Server_new="/data/server_new"
Server_bak="/data/server_bak"
Game_server="/data/dandantang"
Url_ip="125.90.88.20"
sqsjtbj_ip="113.107.161.31"
updateHostName=`hostname`
echo ${updateHostName}|grep -q -i kuaf && Updatetype="kuafu" || Updatetype="web"
Action="$1"

case "$Action" in
        installweb)
            #Install_web
            echo install web fail.
            ;;
        installmanual)
            Install_manual
            ;;
        replace)
            shift
            Replace $*
          ;;
        file_ready)
            File_ready
          ;;
        file_update)
            File_update
          ;;
        start)
            Start_game
          ;;
        changever)
            Change_Ver
          ;;
        createxml)
            CreateAllXml
          ;;
        stop)
            Stop_game stop 330
          ;;
        stopnow)
            Stop_game stopnow 5
          ;; 
        restart)
            Stop_game stopnow 5
            Start_game
          ;;
        stopccu)
            Ccu_stop
          ;;
        startccu)
           Ccu_start
          ;;
        reccu)
           Ccu_stop
           Ccu_start
          ;;
        autoupdate)
           Auto_update 
           ;;
        autostop)
           Auto_stop
           ;;
        autostart)
           Auto_start
           ;;
       autocover)
           Auto_cover
           ;;
        check)
            Check_init
            Check
                  ;;
        alterconfig)
            Check_network
            Select_template_tbj
            Alter_config
              ;;
                *)
                   Usage
                  ;;
        esac
}
main $*
