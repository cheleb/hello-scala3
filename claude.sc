//> using scala 3.10.0
//> using dep "org.virtuslab::orca:0.2.1"
//> using jvm 21

import orca.{*, given}

flow(OrcaArgs(args)):
  stage("Run"):
    
    val session = codingAgent.session("run", seed = userPrompt)
    session.run(userPrompt)