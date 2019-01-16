def call(String cookbook){
    sh """
        COOKBOOK_VERSION=`cat cookbook/${cookbook}/metadata.rb | grep ^version | awk '{print $2}' | sed "s/'//g"`
        NEW_COOOKBOOK_VERSION=`echo ${COOKBOOK_VERSION} | awk -F. -v OFS=. 'NF==1{print ++$NF}; NF>1{if(length($NF+1)>length($NF))$(NF-1)++; $NF=sprintf("%0*d", length($NF), ($NF+1)%(10^length($NF))); print}'`
        sed -i "s|version '${COOOKBOOK_VERSION}|version '${NEW_COOOKBOOK_VERSION}|g" metadata.rb
        git commit -am "Updating cookbook version to: '${NEW_COOOKBOOK_VERSION}'" &&\n git push origin HEAD:'${env.BRANCH_NAME}'
    """
}
