export interface Module {
    isPackageInstalled(packageId: string): Promise<boolean>;
    startActivity(args: {
        action: string;
        type?: string;
        package?: string;
        data?: string;
        extra_stream?: string;
        extras?: {
            [key: string]: string | number | boolean;
        };
    }): Promise<void>;
    startActivityForResult(args: {
        action: string;
        type?: string;
        package?: string;
        data?: string;
        extra_stream?: string;
        extras?: {
            [key: string]: string | number | boolean;
        };
    }): Promise<{
        result: number;
    }>;
}
export declare const Module: Module | undefined;
