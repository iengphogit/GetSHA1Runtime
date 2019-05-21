package com.zero.getsha1runtime;

public enum SignedCertificate {
    Debug("...", null, true, true),
    Staging("...", Environments.Staging, false, true),
    Production("...", Environments.Production, false, false),
    Unknown("There is no such a certificate", Environments.Production, false, false),
    ;

    private final String md5;

    private final Environments environment;

    private final boolean debugMode;

    private final boolean showsLogs;

    private SignedCertificate(String md5, Environments environment, boolean debugMode, boolean showsLogs) {
        this.md5 = md5;
        this.environment = environment;
        this.debugMode = debugMode;
        this.showsLogs = showsLogs;
    }

    public static final SignedCertificate getCertificate(String md5) {
        md5 = md5.toLowerCase();
        for (SignedCertificate certificate : values()) {
            if (certificate.md5.toLowerCase().equals(md5))
                return certificate;
        }
        return Unknown;
    }
}
