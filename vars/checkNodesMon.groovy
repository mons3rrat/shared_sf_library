def call (){
 Jenkins jenkins = Jenkins.instance
 def jenkinsNodes = jenkins.nodes
  for (Node node in jenkinsNodes)
        {
            if (!node.getComputer().isOffline())
            {
                if(node.getComputer().countBusy()==0)
                {
                    println "'$node.nodeName' can take jobs !!!"
                }
                else
                {
                    println "'$node.nodeName' is busy !!!"
                }
            }
             else
            {
                println "'$node.nodeName' is offline !!!"
            }
        }
}
